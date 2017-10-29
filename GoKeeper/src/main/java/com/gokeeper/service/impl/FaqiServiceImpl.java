package com.gokeeper.service.impl;

import com.gokeeper.dataobject.*;
import com.gokeeper.dto.TtpDetailDto;
import com.gokeeper.enums.NewsStatusEnum;
import com.gokeeper.enums.NewsTemplate;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.enums.TtpStatusEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.handler.WebSocketPushHandler;
import com.gokeeper.repository.*;
import com.gokeeper.service.FaqiService;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.utils.KeyUtil;
import com.gokeeper.vo.news.TtpNewsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import javax.transaction.Transactional;
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

        //1.设置下订单id(是个随机，这里调用了根据时间产生16位随机数的方法)
        String ttpId = KeyUtil.genUniqueKey();

        //2.把剩下的属性再设置好,ttp详情入库
        ttpDetailDto.setTtpId(ttpId);
        ttpDetailDto.setAllMoney(ttpDetailDto.getJoinMoney());
        ttpDetailDto.setTtpStatus(TtpStatusEnum.READY.getCode());
        TtpDetail ttpDetail = new TtpDetail();
        BeanUtils.copyProperties(ttpDetailDto, ttpDetail);
        tTpDetailRepository.save(ttpDetail);

        //3.user-ttp关联信息入库
        UserTtp userTtp = new UserTtp();
        //根据ttpid和userid生成
        userTtp.setUserTtpId(ttpId+ttpDetailDto.getUserId());
        userTtp.setUserId(ttpDetailDto.getUserId());
        userTtp.setTtpId(ttpId);
        userTtp.setUserDayBouns(zeroBouns);
        //用户个人得到的总奖金，初始也为0
        userTtp.setUserTotalBouns(zeroBouns);
        userTtp.setPayStatus(0);
        //设置开始进度为0
        userTtp.setTtpSchedule(0);
        userTtpRepository.save(userTtp);

        //4.创建此ttp的用户完成情况记录表
        try{
            //根据开始时间和结束时间算出中间日期
            List<String> datelist = DateUtil.getBetweenDates(ttpDetailDto.getStartTime(), ttpDetailDto.getFinishTime());
            for(int i=0; i<datelist.size(); i++){
                UserRecord userRecord = new UserRecord();
                userRecord.setUserRecordId(ttpId + datelist.get(i));
                userRecord.setUserTtpId(userTtp.getUserTtpId());
                userRecord.setDays(DateUtil.StringToDate1(datelist.get(i)));
                userRecord.setDayStatus(0);
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
        ttpNews.setId(userTtp.getUserTtpId());
        ttpNews.setTtpId(ttpId);
        ttpNews.setUserId(ttpDetailDto.getUserId());
        ttpNews.setNewstype(NewsStatusEnum.NO_READ.getCode());
        UserInfo userInfo = userInfoRepository.findByUserId(ttpDetailDto.getUserId());
        ttpNews.setUsername(userInfo.getUsername());
        ttpNews.setUserIcon(userInfo.getUserIcon());
        ttpNews.setNewsname(ttpDetailDto.getTtpName());
        ttpNews.setNewsstatus(NewsStatusEnum.NO_READ.getCode());
        ttpNews.setStartTime(ttpDetailDto.getStartTime());
        ttpNews.setFinishTime(ttpDetailDto.getFinishTime());
        ttpNews.setPreviewText(NewsTemplate.createTtpNews(ttpNews.getNewsname(), DateUtil.dateFormat2(ttpNews.getStartTime(), 0 ,16)));
        //设置为公开
        ttpNews.setHidden(1);
        //权重设置为1
        ttpNews.setWeight(1);
        //新的ttpnews入库
        ttpNewsRepository.save(ttpNews);

        //2.调用websocket方法，发送消息，还不知道需不需要，因为不知道如果用户在别的页面这个消息会不会刷新，如果不会就不用发其实
        TextMessage t = new TextMessage(JsonUtil.toJson(ttpNews));
        WebSocketPushHandler.sendMessageToUser(ttpNews.getUserId(), t);

        return ttpDetailDto;
    }
}
