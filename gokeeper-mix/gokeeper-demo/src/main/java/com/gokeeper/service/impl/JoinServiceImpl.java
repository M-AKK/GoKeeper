package com.gokeeper.service.impl;

import com.gokeeper.dataobject.*;
import com.gokeeper.enums.*;
import com.gokeeper.exception.TTpException;
import com.gokeeper.handler.WebSocketPushHandler;
import com.gokeeper.repository.*;
import com.gokeeper.service.JoinService;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.EnumUtil;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.vo.JoinPreVo;
import com.gokeeper.vo.JoinVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.gokeeper.utils.DateUtil.dateFormat2;

/**
 * 加入具体实现
 * @author:Created by Akk_Mac
 * @Date: 2017/10/7 09:45
 */
@Service
@Slf4j
public class JoinServiceImpl implements JoinService {

    @Autowired
    private TTpDetailRepository tTpDetailRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserTtpRepository userTtpRepository;

    @Autowired
    private UserRecordRepository userRecordRepository;

    @Autowired
    private TtpNewsRepository ttpNewsRepository;

    /**
     * 加入界面列表展示
     * @return
     */
    @Override
    public List<JoinPreVo> getOpenTtp() {
        //1.获取所有公开并且ttp状态不是完结按创建时间进行排序的ttp
        List<TtpDetail> openttpdetaillist = tTpDetailRepository.findByIfOpen(IfOpenEnum.YES.getCode(), TtpStatusEnum.FINISH.getCode());
        List<JoinPreVo> joinPreVoList = getjoinPreVoList(openttpdetaillist);
        return joinPreVoList;
    }

    @Override
    public JoinVo getOneTtp(String ttpId){
        //1.首先查找对应ttp详情信息
        TtpDetail detail = tTpDetailRepository.findByTtpId(ttpId);
        JoinVo joinVo = new JoinVo();

        //2.根据userid查找userinfo
        UserInfo userInfo = userInfoRepository.findByUserId(detail.getUserId());
        joinVo.setUsername(userInfo.getUsername());
        joinVo.setUserIcon(userInfo.getUserIcon());
        //设置ttp相关信息
        joinVo.setTtpId(detail.getTtpId());
        joinVo.setTtpName(detail.getTtpName());
        joinVo.setTtpType(EnumUtil.getByCode(detail.getTtpType(), TtpTypeEnum.class).getMessage());
        joinVo.setCreateTime(dateFormat2(detail.getCreateTime(), 0,16));
        joinVo.setStartTime(dateFormat2(detail.getStartTime(), 0,16));
        joinVo.setFinishTime(dateFormat2(detail.getFinishTime(), 0, 16));
        //TODO 输入目标数，返回对应结果,现在假定运动类ttp才有目标
        if(detail.getTtpType().equals(TtpTypeEnum.SPORTS.getCode())){
            joinVo.setTtpTarget(TtpTargetTemplate.runenum(detail.getTtpTarget()));
        }

        joinVo.setJoinMoney(detail.getJoinMoney());
        joinVo.setAllMoney(detail.getAllMoney());
        //设置当前参与总人数
        joinVo.setJoinPeopleNums(userTtpRepository.findByTtpId(detail.getTtpId()).size());
        joinVo.setLeaveNotesNums(detail.getLeaveNotesNums());
        joinVo.setIfJoin(EnumUtil.getByCode(detail.getIfJoin(), IfJoinEnum.class).getMessage());
//        joinVo.setIfQuit(EnumUtil.getByCode(detail.getIfQuit(), IfQuitEnum.class).getMessage());

        return joinVo;
    }

    /**
     * 加入功能，把用户加入到已知ttp列表中
     * @param userId
     * @param ttpId
     * @return
     */
    @Override
    @Transactional//事务管理，一旦失败就回滚
    public UserTtp attend(String userId, String ttpId){
        //1.判断此用户是否重复加入此ttp
        List<UserTtp> userTtpList = userTtpRepository.findByUserIdOrderByUpdateTimeDesc(userId);
        for(UserTtp userTtp : userTtpList) {
            if(ttpId.equals(userTtp.getTtpId())){
                throw new TTpException(ResultEnum.USER_JOIN_REPEAT);
            }
        }

        //设置起始每日奖金总额为0
        BigDecimal zeroBouns = new BigDecimal(BigInteger.ZERO);
        TtpDetail ttpDetail = tTpDetailRepository.findByTtpId(ttpId);
        //2.生成user-ttp关联表信息
        UserTtp userTtp = new UserTtp();
        //根据ttpid和userid生成
        userTtp.setUserTtpId(userId+ttpId);
        userTtp.setUserId(userId);
        userTtp.setTtpId(ttpId);
        userTtp.setUserDayBouns(zeroBouns);
        //用户个人得到的总奖金，初始也为0
        userTtp.setUserTotalBouns(zeroBouns);
        userTtp.setPayStatus(0);
        //设置开始进度为0
        userTtp.setTtpSchedule(0);
        userTtp.setUserTtpStatus(ttpDetail.getTtpStatus());
        userTtpRepository.save(userTtp);
        //查找ttpDetail信息

        //用户记录表录入
        try{
            //根据开始时间和结束时间算出中间日期
            List<String> datelist = DateUtil.getBetweenDates(ttpDetail.getStartTime(), ttpDetail.getFinishTime());
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

        /**TODO 消息推送2：
         当用户点击加入此ttp按钮后点击下一步时到达支付页面之前，后台会有个消息推送为:
         发送ttp消息模板：你成功加入xxttp，请在活动时间(time)开始前缴纳启动金：支付链接
         用户只有在创建后没有支付的情况下看到这条消息，如果用户继续支付成功就会看到不一样的消息
         接下来要写支付成功后修改对应ttp消息的方法
         */
        //1.创建ttp消息模板
        TtpNews ttpNews = new TtpNews();
        //ttpNews的id就是user-ttp的id，一条用户ttp对应一条模板
        ttpNews.setUserTtpId(userTtp.getUserTtpId());
        ttpNews.setTtpId(ttpId);
        ttpNews.setUserId(userId);
        ttpNews.setTtpStatus(TtpStatusEnum.READY.getCode());
        ttpNews.setNewstype(ttpDetail.getTtpStatus());
        UserInfo userInfo = userInfoRepository.findByUserId(ttpDetail.getUserId());
        ttpNews.setUsername(userInfo.getUsername());
        ttpNews.setUserIcon(userInfo.getUserIcon());
        ttpNews.setNewsname(ttpDetail.getTtpName());
        ttpNews.setNewsstatus(NewsStatusEnum.NO_READ.getCode());
        ttpNews.setStartTime(ttpDetail.getStartTime());
        ttpNews.setFinishTime(ttpDetail.getFinishTime());
        ttpNews.setPreviewText(NewsTemplate.joinTtpNews(ttpNews.getNewsname(), DateUtil.dateFormat2(ttpNews.getStartTime(), 0 ,16)));
        //设置为公开
        ttpNews.setHidden(1);
        //权重设置为1
        ttpNews.setWeight(1);
        //新的ttpnews入库
        ttpNewsRepository.save(ttpNews);

        //2.调用websocket方法，发送消息，还不知道需不需要，因为不知道如果用户在别的页面这个消息会不会刷新，如果不会就不用发其实
        TextMessage t = new TextMessage(JsonUtil.toJson(ttpNews));
        WebSocketPushHandler.sendMessageToUser(ttpNews.getUserId(), t);

        return userTtp;
    }


    @Override
    public UserTtp paid(UserTtp userTtp) {

        TtpDetail ttpDetail = tTpDetailRepository.findByTtpId(userTtp.getTtpId());

        //1.判断ttp状态是否为已完结
        if(ttpDetail.getTtpStatus().equals(TtpStatusEnum.FINISH.getCode())) {
            log.error("[TTP支付完成] TTP状态不正确，userTtpId={}, ttpStatus={}", userTtp.getUserTtpId(), ttpDetail.getTtpStatus());
            throw new TTpException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.判断支付状态
        if(!userTtp.getPayStatus().equals(PayEnum.NO.getCode())){
            log.error("[TTP支付完成] TTP支付状态不正确，userTtp={}", userTtp);
            throw new TTpException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //3.修改支付状态
        userTtp.setPayStatus(PayEnum.YES.getCode());
        UserTtp updateResult = userTtpRepository.save(userTtp);
        if(updateResult == null){
            log.error("[TTP支付成功] 更新失败， userTtp={}", userTtp);
            throw new TTpException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return userTtp;
    }

    //加入预览信息查询后的转换方法
    private List<JoinPreVo> getjoinPreVoList(List<TtpDetail> openttpdetaillist) {
        List<JoinPreVo> joinPreVoList = new ArrayList<>();
        for(TtpDetail detail : openttpdetaillist) {
            JoinPreVo joinPreVo = new JoinPreVo();
            //2.根据userid查找userinfo
            UserInfo userInfo = userInfoRepository.findByUserId(detail.getUserId());
            joinPreVo.setUsername(userInfo.getUsername());
            joinPreVo.setUserIcon(userInfo.getUserIcon());
            //设置ttp相关信息
            joinPreVo.setTtpId(detail.getTtpId());
            joinPreVo.setTtpName(detail.getTtpName());
            joinPreVo.setTtpType(EnumUtil.getByCode(detail.getTtpType(), TtpTypeEnum.class).getMessage());
            joinPreVo.setCreateTime(dateFormat2(detail.getCreateTime(), 0,16));
            joinPreVo.setStartTime(dateFormat2(detail.getStartTime(), 0,16));

            //TODO 输入目标数，返回对应结果,现在假定运动类ttp才有目标
            if(detail.getTtpType().equals(TtpTypeEnum.SPORTS.getCode())){
                joinPreVo.setTtpTarget(TtpTargetTemplate.runenum(detail.getTtpTarget()));
            }
            joinPreVo.setAllMoney(detail.getAllMoney());
            //设置当前参与总人数
            joinPreVo.setJoinPeopleNums(userTtpRepository.findByTtpId(detail.getTtpId()).size());
            joinPreVoList.add(joinPreVo);
        }
        return joinPreVoList;
    }


}
