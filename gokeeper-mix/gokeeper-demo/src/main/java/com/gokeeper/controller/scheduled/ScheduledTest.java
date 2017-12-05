package com.gokeeper.controller.scheduled;

import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dataobject.TtpNews;
import com.gokeeper.dataobject.UserRecord;
import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.enums.*;
import com.gokeeper.handler.WebSocketPushHandler;
import com.gokeeper.repository.TTpDetailRepository;
import com.gokeeper.repository.TtpNewsRepository;
import com.gokeeper.repository.UserRecordRepository;
import com.gokeeper.repository.UserTtpRepository;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.utils.converter.NewsBeanZNewsDtoConverter;
import com.gokeeper.utils.converter.NewsDtoZNewsVoConverter;
import com.gokeeper.vo.news.TtpNewsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 定时任务类，主要发送每日ttp战况总结
 * @author: Created by Akk_Mac
 * @Date: 2017/10/29 10:24
 */
@Component
@Slf4j
public class ScheduledTest {

    @Autowired
    private UserTtpRepository userTtpRepository;

    @Autowired
    private TTpDetailRepository tTpDetailRepository;

    @Autowired
    private TtpNewsRepository ttpNewsRepository;

    @Autowired
    private UserRecordRepository userRecordRepository;


    @Scheduled(cron = "0 0 21 * * ?")
    /**
     * 每天21点给每个用户发送战况总结
     */
    public void sandDaysTtpNews() {
        //消息模板已经建立好，现在需要做的只用每日修改下ttp的消息模板信息
        //获取系统当天时间：格式2017/10/30
        String today = DateUtil.dateFormat2(new Date(), 0, 10);

        //1.查找所有的ttp详情，判断此ttp状态是不是进行中，ttp每日推送消息基础建立在进行中的状态上
        List<TtpDetail> ttpDetailList = tTpDetailRepository.findAll();
        for(TtpDetail ttpDetail : ttpDetailList) {
            int finishnums=0,nofinishnums=0,leavenums=0;

            if(ttpDetail.getTtpStatus().equals(TtpStatusEnum.WORKING.getCode())) {
                //2.判断如果此ttp状态在进行中，查找出此user-ttp关联列表，就是知道哪些用户参与此ttp，依次设置对应消息模板
                List<UserTtp> userTtpList = userTtpRepository.findByTtpId(ttpDetail.getTtpId());
                //3.先设置好公共属性：完成、未完成人数、请假人数
                for(UserTtp conmmonUserTtp : userTtpList) {
                    UserRecord userRecord0 = userRecordRepository.findByUserRecordId(conmmonUserTtp.getUserTtpId() + today);
                    if(userRecord0.getDayStatus().equals(DayStatusEnum.FINIS.getCode())) {
                        finishnums++;
                    } else if (userRecord0.getDayStatus().equals(DayStatusEnum.NO_FINISH.getCode())) {
                        nofinishnums++;
                    } else if (userRecord0.getDayStatus().equals(DayStatusEnum.QINGJIA.getCode())) {
                        leavenums++;
                    }
                }
                //4.单独设置每个用户的个人消息属性
                for(UserTtp userTtp : userTtpList) {
                    if(userTtp.getUserTtpStatus().equals(UserTtpStatusEnum.WORKING.getCode())) {
                        TtpNews ttpNews = ttpNewsRepository.findByUserTtpId(userTtp.getUserTtpId());
                        //5.新消息要重新改变news的状态为未读
                        ttpNews.setNewsstatus(NewsStatusEnum.NO_READ.getCode());
                        ttpNews.setTtpStatus(userTtp.getUserTtpStatus());
                        UserRecord userRecord = userRecordRepository.findByUserRecordId(userTtp.getUserTtpId()+today);
                        //是否完成：直接按照数据库的记录设置就行了
                        ttpNews.setIfFinish(userRecord.getDayStatus());
                        //完成人数
                        ttpNews.setFinishnums(finishnums);
                        ttpNews.setNofinishnums(nofinishnums);
                        ttpNews.setLeaveNotes(ttpDetail.getLeaveNotesNums()-userTtp.getLeaveNotes());
                        ttpNews.setLeavenums(leavenums);
                        ttpNews.setUserDayBouns(userTtp.getUserDayBouns());
                        ttpNews.setUserTotalBouns(userTtp.getUserTotalBouns());
                        //6.新消息入库
                        ttpNewsRepository.save(ttpNews);
                        //7.转化消息类型为Vo并发送websocket
                        //调用websocket方法，发送消息，还不知道需不需要，因为不知道如果用户在别的页面这个消息会不会刷新，如果不会就不用发其实
                        TextMessage t = new TextMessage(JsonUtil.toJson(listTtpNewsZttpNews(ttpNews)));
                        WebSocketPushHandler.sendMessageToUser(ttpNews.getUserId(), t);
                    }
                }
            }
        }
    }

    @Scheduled(cron = "*/10 * * * * ?")  //每10秒执行一次
    /**
     * 检测ttp状态是否改变为  进行时/完结/用户退出
     */
    public void updateTtpStatus() {
        //1.查找所有ttp详情
        List<TtpDetail> ttpDetailList = tTpDetailRepository.findAll();
        //2.遍历每个ttp
        for(TtpDetail ttpDetail : ttpDetailList) {
            Date startTime = ttpDetail.getStartTime();
            Date finishTime = ttpDetail.getFinishTime();
            //1.获取系统当前时间
            Date currentTime = new Date();
            //2.如果处于进行时状态时
            if(currentTime.after(startTime) && currentTime.before(finishTime)) {
                    //2-1.改变ttp状态
                    ttpDetail.setTtpStatus(TtpStatusEnum.WORKING.getCode());
                    tTpDetailRepository.save(ttpDetail);
                    //2-2.改变userTtpStatus状态
                    List<UserTtp> userTtpList = userTtpRepository.findByTtpId(ttpDetail.getTtpId());
                    for(UserTtp userTtp : userTtpList) {
                        //3.判断活动开始了后，用户的支付状态
                        if(userTtp.getPayStatus().equals(PayEnum.YES.getCode())) {
                            userTtp.setUserTtpStatus(UserTtpStatusEnum.WORKING.getCode());
                            userTtpRepository.save(userTtp);
                        //未支付：
                        } else if(userTtp.getPayStatus().equals(PayEnum.NO.getCode())) {
                            TtpNews ttpNews = ttpNewsRepository.findByUserTtpId(userTtp.getUserTtpId());
                            //4.把ttpNews的状态和预览消息改变，前端如果判断status就可以改变样式了
                            ttpNews.setPreviewText(NewsTemplate.noPayTtpNews());
                            //5.存入初始化有值的ttpNews
                            TtpNews result = createTtpNews(ttpNews);
                            ttpNewsRepository.save(result);
                        }
                    }
            } /*3.到达完结状态*/
                else if(currentTime.after(finishTime)) {
                    //2-1.改变ttp状态
                    ttpDetail.setTtpStatus(TtpStatusEnum.FINISH.getCode());
                    tTpDetailRepository.save(ttpDetail);
                    //2-2.改变userTtpStatus状态
                    List<UserTtp> userTtpList = userTtpRepository.findByTtpId(ttpDetail.getTtpId());
                    for(UserTtp userTtp : userTtpList) {
                        //3.首先发送消息模板
                        userTtp.setUserTtpStatus(UserTtpStatusEnum.FINISH.getCode());
                        userTtpRepository.save(userTtp);
                }
            }
        }
    }

    @Scheduled(cron = "*/10 * * * * ?")  //每10秒执行一次
    /**
     * 检测ttp状态是否为完结状态并进行奖金返还操作
     */
    public void updateTtpStatus2() {

    }

    /**
     * 将TtpNews部分新属性改变并返回，减小方法体容量
     * @param ttpNews
     * @return
     */
    public static TtpNews createTtpNews(TtpNews ttpNews) {
        //新消息要重新改变news的状态为未读
        ttpNews.setNewsstatus(NewsStatusEnum.NO_READ.getCode());
        //初始化设置为未完成
        ttpNews.setIfFinish(DayStatusEnum.NO_FINISH.getCode());
        ttpNews.setFinishnums(0);
        ttpNews.setNofinishnums(0);
        ttpNews.setLeaveNotes(0);
        ttpNews.setLeavenums(0);
        //今日奖金初始化为0
        ttpNews.setUserDayBouns(new BigDecimal(0));
        //用户获得总监金初始化为0
        ttpNews.setUserTotalBouns(new BigDecimal(0));
        return ttpNews;
    }

    /**
     * 由于Allmessage方法里面转化方法用的是List，所以这里用List转化一条消息
     * @param ttpNews
     * @return
     */
    public static TtpNewsVo listTtpNewsZttpNews(TtpNews ttpNews) {
        List<TtpNews> ttpNewsList1 = new ArrayList<>();
        ttpNewsList1.add(ttpNews);
        List<TtpNewsVo> ttpNewsVoList = NewsDtoZNewsVoConverter.TtpNewsDtoZVoconvert(NewsBeanZNewsDtoConverter.TtpNewsZDtoconvert(ttpNewsList1));
        //由于是list，默认调用第一条消息
        return ttpNewsVoList.get(0);
    }

}
