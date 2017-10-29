package com.gokeeper.controller.api;

import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dataobject.TtpNews;
import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.enums.NewsStatusEnum;
import com.gokeeper.enums.TtpStatusEnum;
import com.gokeeper.handler.WebSocketPushHandler;
import com.gokeeper.repository.TTpDetailRepository;
import com.gokeeper.repository.TtpNewsRepository;
import com.gokeeper.repository.UserTtpRepository;
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
 * @Description: 定时任务类，发送每日ttp战况总结
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


    @Scheduled(cron = "0 0 21 * * ?")
    /**
     * 每天21点给每个用户发送战况总结
     */
    public void sandTtpNews() {
        //消息模板已经建立好，现在需要做的只用每日修改下ttp的消息模板信息
        //1.查找所有的ttp消息模板，判断他的状态是不是进行中
        List<UserTtp> userTtpList = userTtpRepository.findAll();
        for(int i=0; i<userTtpList.size(); i++){
            TtpDetail ttpDetail = tTpDetailRepository.findByTtpId(userTtpList.get(i).getTtpId());
            //如果是在进行中的ttp才会发送ttp消息
            if(ttpDetail.getTtpStatus().equals(TtpStatusEnum.WORKING.getCode())) {
                //2.进行消息数据统计并生成模板
                //3.发送数据
            }
        }

    }

    @Scheduled(cron = "*/10 * * * * ?")  //每10秒执行一次
    /**
     * 更新ttp的状态，每10秒进行检测，如果系统当前时间>=startTime，就改变状态
     */
    public void updateTtpStatus() {

        List<TtpDetail> ttpDetailList = tTpDetailRepository.findAll();
        for(TtpDetail ttpDetail : ttpDetailList) {
            Date startTime = ttpDetail.getStartTime();
            Date finishTime = ttpDetail.getFinishTime();
            //获取系统当前时间
            Date currentTime = new Date();
            //如果处于进行时状态时
            if(currentTime.after(startTime) && currentTime.before(finishTime)) {
                //如果ttp的状态改变了
                ttpDetail.setTtpStatus(TtpStatusEnum.WORKING.getCode());
                tTpDetailRepository.save(ttpDetail);
                //改变返回给前端的消息模板
                List<TtpNews> ttpNewsList = ttpNewsRepository.findByTtpId(ttpDetail.getTtpId());
                for(TtpNews ttpNews : ttpNewsList) {
                    //把ttpNews的预览消息设置为空，前端如果判断为空就可以改变样式了
                    ttpNews.setPreviewText("");
                    //存入初始化有值的ttpNews
                    TtpNews result = createTtpNews(ttpNews);
                    ttpNewsRepository.save(result);
                    //由于转化方法用的是List，所以这里用List转化一条消息
                    List<TtpNews> ttpNewsList1 = new ArrayList<>();
                    ttpNewsList1.add(result);
                    List<TtpNewsVo> ttpNewsVoList = NewsDtoZNewsVoConverter.TtpNewsDtoZVoconvert(NewsBeanZNewsDtoConverter.TtpNewsZDtoconvert(ttpNewsList1));
                    //2.调用websocket方法，发送消息，还不知道需不需要，因为不知道如果用户在别的页面这个消息会不会刷新，如果不会就不用发其实
                    // 默认调用第一条消息
                    TextMessage t = new TextMessage(JsonUtil.toJson(ttpNewsVoList.get(0)));
                    WebSocketPushHandler.sendMessageToUser(ttpNews.getUserId(), t);

                }
                log.info("系统时间小于ttp开始时间,{},{}",currentTime,startTime);
            }
        }

    }

    public static TtpNews createTtpNews(TtpNews ttpNews) {
        //新消息要重新改变news的状态为未读
        ttpNews.setNewsstatus(NewsStatusEnum.NO_READ.getCode());
        ttpNews.setIfFinish(0);
        ttpNews.setFinishnums(0);
        ttpNews.setNofinishnums(0);
        ttpNews.setLeavenums(0);
        ttpNews.setUserDayBouns(new BigDecimal(1));
        ttpNews.setUserTotalBouns(new BigDecimal(10));
        return ttpNews;
    }
}
