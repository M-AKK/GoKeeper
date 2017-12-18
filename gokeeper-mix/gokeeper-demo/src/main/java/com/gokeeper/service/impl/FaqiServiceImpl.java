package com.gokeeper.service.impl;

import com.gokeeper.dataobject.*;
import com.gokeeper.dto.TtpDetailDto;
import com.gokeeper.enums.*;
import com.gokeeper.exception.TTpException;
import com.gokeeper.handler.WebSocketPushHandler;
import com.gokeeper.repository.*;
import com.gokeeper.service.FaqiService;
import com.gokeeper.service.WxPayService;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.utils.KeyUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 * 发起界面操作的具体实现
 * @author Created by Akk_Mac
 * @Date: 2017/10/1 22:57
 */
@Service
@Slf4j
public class FaqiServiceImpl implements FaqiService {

    @Autowired
    private TTpDetailRepository tTpDetailRepository;

    @Autowired
    private UserTtpRepository userTtpRepository;

    @Autowired
    private UserRecordRepository userRecordRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private TtpNewsRepository ttpNewsRepository;

    @Autowired
    private TtpTypeRepository ttpTypeRepository;

    @Autowired
    private WxPayService wxPayService;

    /**
     * 创建一个新ttp
     * @param ttpDetailDto
     * @return
     */
    @Override
    @Transactional//事务管理，一旦失败就回滚
    public TtpDetailDto create(TtpDetailDto ttpDetailDto) {
        //设置起始每日奖金总额为0
        BigDecimal zeroBouns = new BigDecimal(BigInteger.ZERO);

        //1.设置下订单id(是个随机，这里调用了根据时间产生32位随机数的方法)
        String ttpId = KeyUtil.genUniqueKey();

        //2.把剩下的属性再设置好,ttp详情入库
        ttpDetailDto.setTtpId(ttpId);
        ttpDetailDto.setAllMoney(ttpDetailDto.getJoinMoney());
        ttpDetailDto.setTtpStatus(TtpStatusEnum.READY.getCode());
        TtpDetail ttpDetail = new TtpDetail();
        BeanUtils.copyProperties(ttpDetailDto, ttpDetail);
        tTpDetailRepository.save(ttpDetail);
        //2-1：判断自己是否参加
        if(ttpDetail.getJoinSelf().equals(JoinSelfEnum.YES.getCode())) {
            //3.自己也参加才把user-ttp关联信息入库
            UserTtp userTtp = new UserTtp();
            //根据userid+ttpid生成
            userTtp.setUserTtpId(ttpDetailDto.getUserId()+ttpId);
            userTtp.setUserId(ttpDetailDto.getUserId());
            userTtp.setTtpId(ttpId);
            userTtp.setUserDayBouns(zeroBouns);
            //用户个人得到的总奖金，初始也为0
            userTtp.setUserTotalBouns(zeroBouns);
            userTtp.setPayStatus(PayEnum.NO.getCode());
            userTtp.setUserTtpStatus(UserTtpStatusEnum.READY.getCode());
            //设置开始进度为0
            userTtp.setTtpSchedule(0);
            userTtpRepository.save(userTtp);

            //4.创建此ttp的用户完成情况记录表
            try{
                //根据开始时间和结束时间算出中间日期，如果是聚餐类只有一天则会算出当天日期
                List<String> datelist = DateUtil.getBetweenDates(ttpDetailDto.getStartTime(), ttpDetailDto.getFinishTime());
                for(int i=0; i<datelist.size(); i++){
                    UserRecord userRecord = new UserRecord();
                    userRecord.setUserRecordId(userTtp.getUserTtpId() + datelist.get(i));
                    userRecord.setUserTtpId(userTtp.getUserTtpId());
                    userRecord.setDays(DateUtil.StringToDate1(datelist.get(i)));
                    userRecord.setDayStatus(DayStatusEnum.NO_FINISH.getCode());
                    userRecordRepository.save(userRecord);
                }
            } catch(ParseException e) {
                log.error("【日期转换出问题】");
                throw new TTpException(ResultEnum.CREATE_ERROR);
            }

            /**TODO 消息推送1：
             当用户填写完ttp基本信息后点击下一步时到达支付页面之前，后台会有个消息推送为:
             发送ttp消息模板：你成功创建xxttp，请在活动时间(time)开始前缴纳启动金：支付链接
             用户只有在创建后没有支付的情况下看到这条消息，如果用户继续支付成功就会看到不一样的消息
             接下来要写支付成功后修改对应ttp消息的方法
             */
            //1.创建ttp消息模板
            TtpNews ttpNews = new TtpNews();
            //ttpNews的id就是user-ttp的id，一条用户ttp对应一条模板
            ttpNews.setUserTtpId(userTtp.getUserTtpId());
            ttpNews.setTtpId(ttpId);
            ttpNews.setUserId(ttpDetailDto.getUserId());
            ttpNews.setTtpStatus(TtpStatusEnum.READY.getCode());
            ttpNews.setNewstype(NewsTypeEnum.TTP.getCode());
            UserInfo userInfo = userInfoRepository.findByUserId(ttpDetailDto.getUserId());
            ttpNews.setUsername(userInfo.getUsername());
            ttpNews.setUserIcon(userInfo.getUserIcon());
            ttpNews.setNewsname(ttpDetailDto.getTtpName());
            ttpNews.setNewsstatus(NewsStatusEnum.NO_READ.getCode());
            ttpNews.setStartTime(ttpDetailDto.getStartTime());
            ttpNews.setFinishTime(ttpDetailDto.getFinishTime());
            ttpNews.setUserTotalBouns(zeroBouns);
            ttpNews.setIfFinish(DayStatusEnum.NO_FINISH.getCode());
            ttpNews.setFinishnums(0);
            ttpNews.setNofinishnums(0);
            ttpNews.setLeaveNotes(0);
            ttpNews.setLeavenums(0);
            //今日奖金初始化为0
            ttpNews.setUserDayBouns(new BigDecimal(0));
            //用户获得总监金初始化为0
            ttpNews.setUserTotalBouns(new BigDecimal(0));
            String preViewText = NewsTemplate.createTtpNews(ttpNews.getNewsname(), DateUtil.dateFormat2(ttpNews.getStartTime(), 0, 16));
            log.info("预览消息"+preViewText);
            ttpNews.setPreviewText(preViewText);
            //设置为公开
            ttpNews.setHidden(IfOpenEnum.YES.getCode());
            //权重设置为1
            ttpNews.setWeight(1);
            //新的ttpnews入库
            TtpNews ttpNews1 = ttpNewsRepository.save(ttpNews);
            //2.调用websocket方法，发送消息，还不知道需不需要，因为不知道如果用户在别的页面这个消息会不会刷新，如果不会就不用发其实
            TextMessage t = new TextMessage(JsonUtil.toJson(ttpNews));
            WebSocketPushHandler.sendMessageToUser(ttpNews.getUserId(), t);
        }

        return ttpDetailDto;
    }

    @Override
    public List<TtpType> findAllType() {
        List<TtpType> ttpTypeList = ttpTypeRepository.findAll();
        return ttpTypeList;
    }

    @Override
    @Transactional
    public UserTtp canel(UserTtp userTtp, String currentDate) {
        //1.判断此活动有没有开始，没有开始退出不会扣除金额，原金额返回
        TtpDetail ttpDetail = tTpDetailRepository.findByTtpId(userTtp.getTtpId());

        //2.修改userTtp的状态为中途退出
        userTtp.setUserTtpStatus(UserTtpStatusEnum.QUIT.getCode());
        UserTtp updateResult = userTtpRepository.save(userTtp);
        if(updateResult == null){
            log.error("[取消订单] 更新失败， userTtp={}", userTtp);
            throw new TTpException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        BigDecimal orderAmount = new BigDecimal(0);
        if(ttpDetail.getFaqiType().equals(FaqiTypeEnum.GROUP_TYPE)) {
            orderAmount = ttpDetail.getJoinMoney();
        } else {
            orderAmount = ttpDetail.getJoinSelfMoney();
        }
        //3.如果活动还未开始，返还所有加入金额
        if(ttpDetail.getTtpStatus().equals(TtpStatusEnum.READY) && userTtp.getPayStatus().equals(PayEnum.YES)) {

            //4.判断支付方式和支付状态是否为已支付
            if(userTtp.getPayType().equals(BestPayTypeEnum.WXPAY_H5.getCode()) && userTtp.getPayStatus().equals(PayEnum.YES.getCode())) {
                wxPayService.refund(userTtp, orderAmount);
            }
            return userTtp;

        } else if(ttpDetail.getTtpStatus().equals(TtpStatusEnum.WORKING) && userTtp.getPayStatus().equals(PayEnum.YES)) {
            //5.如果活动已经开始，则需要根据当前时间来判断返还的金额
            long allTime = (ttpDetail.getStartTime().getTime() - ttpDetail.getFinishTime().getTime()) / (86400 * 1000);
            long finishTime = (System.currentTimeMillis() - ttpDetail.getStartTime().getTime()) / (86400 * 1000);
            double bili = finishTime * 1.0 / allTime;
            orderAmount = orderAmount.multiply(new BigDecimal(Double.toString(bili)));
            BigDecimal one = new BigDecimal("1");
            BigDecimal orderAmount1 = orderAmount.divide(one,2,BigDecimal.ROUND_HALF_UP);//保留1位数
            if(userTtp.getPayType().equals(BestPayTypeEnum.WXPAY_H5.getCode()) && userTtp.getPayStatus().equals(PayEnum.YES.getCode())) {
                wxPayService.refund(userTtp, orderAmount1);
            }
            return userTtp;

        }
        return null;
    }
}
