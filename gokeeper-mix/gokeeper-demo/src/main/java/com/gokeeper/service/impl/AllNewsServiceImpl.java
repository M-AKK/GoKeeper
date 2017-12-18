package com.gokeeper.service.impl;

import com.gokeeper.dataobject.InviteNews;
import com.gokeeper.dataobject.SystemNews;
import com.gokeeper.dataobject.TtpNews;
import com.gokeeper.enums.NewsStatusEnum;
import com.gokeeper.repository.InviteNewsRepository;
import com.gokeeper.repository.SystemNewsRspository;
import com.gokeeper.repository.TtpNewsRepository;
import com.gokeeper.service.AllNewsService;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.utils.converter.NewsBeanZNewsDtoConverter;
import com.gokeeper.utils.converter.NewsDtoZNewsVoConverter;
import com.gokeeper.vo.news.AllNewsVo;
import com.gokeeper.vo.news.InviteNewsVo;
import com.gokeeper.vo.news.SystemNewsVo;
import com.gokeeper.vo.news.TtpNewsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 信息界面初始化列表
 * @author Created by Akk_Mac
 * @Date: 2017/10/13 19:26
 */
@Service
@Slf4j
public class AllNewsServiceImpl implements AllNewsService {

    @Autowired
    private SystemNewsRspository systemNewsRspository;

    @Autowired
    private TtpNewsRepository ttpNewsRepository;

    @Autowired
    private InviteNewsRepository inviteNewsRepository;

    /**
     * 现在返回的是此用户所有的信息，下一步应该还需要做过滤判断处理
     * @param userId
     * @return
     */
    @Override
    public List<AllNewsVo> findAllOpenMsg(String userId, Integer hidden) {

        //1.转化系统消息到Vo对象
        List<SystemNews> systemNewsList = systemNewsRspository.findAllByHiddenOrderByUpdateTimeDesc(hidden);
        List<TtpNews> ttpNewsList = ttpNewsRepository.findAllByUserIdAndHiddenOrderByUpdateTimeDesc(userId, hidden);
        List<InviteNews> inviteNewsList = inviteNewsRepository.findAllByUserIdAndHiddenOrderByUpdateTimeDesc(userId, hidden);

        //2.转化ttp消息到Vo
        //3.转化邀请消息到对象
        List<SystemNewsVo> systemNewsVoList = NewsDtoZNewsVoConverter.SystemNewsDtoZVoconvert(NewsBeanZNewsDtoConverter.SystemNewsZDtoconvert(systemNewsList));
        List<TtpNewsVo> ttpNewsVoList = NewsDtoZNewsVoConverter.TtpNewsDtoZVoconvert(NewsBeanZNewsDtoConverter.TtpNewsZDtoconvert(ttpNewsList));
        List<InviteNewsVo> inviteNewsVoList = NewsDtoZNewsVoConverter.InviteNewsDtoZVoconvert(NewsBeanZNewsDtoConverter.InviteNewsZDtoconvert(inviteNewsList));

        List<AllNewsVo> allMsgList = new ArrayList<>();
        for(SystemNewsVo systemNewsVo : systemNewsVoList) {
            AllNewsVo allNewsVo1 = new AllNewsVo();
            BeanUtils.copyProperties(systemNewsVo, allNewsVo1);
            allMsgList.add(allNewsVo1);
        }
        for(TtpNewsVo ttpNewsVo : ttpNewsVoList) {
            AllNewsVo allNewsVo2 = new AllNewsVo();
            BeanUtils.copyProperties(ttpNewsVo, allNewsVo2);
            allMsgList.add(allNewsVo2);
        }
        for(InviteNewsVo inviteNewsVo : inviteNewsVoList) {
            AllNewsVo allNewsVo3 = new AllNewsVo();
            BeanUtils.copyProperties(inviteNewsVo, allNewsVo3);
            allMsgList.add(allNewsVo3);
        }
        //对allMsgList按时间进行排序
        ListSort(allMsgList);
        return allMsgList;
    }

    /**
     * 对所有信息按更新时间排序
     * @param list
     */
    private void ListSort(List<AllNewsVo> list) {
        Collections.sort(list, new Comparator<AllNewsVo>() {
            @Override
            public int compare(AllNewsVo o1, AllNewsVo o2) {

                try {
                    Date dt1 = DateUtil.StringToDate(o1.getUpdateTime());
                    Date dt2 = DateUtil.StringToDate(o2.getUpdateTime());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    @Override
    public Object getOneMsg(String msgId) {

        SystemNews systemNews = systemNewsRspository.findById(msgId);
        TtpNews ttpNews = ttpNewsRepository.findByUserTtpId(msgId);
        if (systemNews != null) {
            systemNews.setNewsstatus(NewsStatusEnum.YES_READ.getCode());
            SystemNews result = systemNewsRspository.save(systemNews);
            return NewsDtoZNewsVoConverter.SystemNewsDtoZVoconvert(NewsBeanZNewsDtoConverter.SystemNewsZDtoconvert(result));
        } else if(ttpNews != null ) {
            ttpNews.setNewsstatus(NewsStatusEnum.YES_READ.getCode());
            TtpNews result = ttpNewsRepository.save(ttpNews);
            //log.info("【数据库查询得到】"+JsonUtil.toJson(result));
            return NewsDtoZNewsVoConverter.TtpNewsDtoZVoconvert(NewsBeanZNewsDtoConverter.TtpNewsZDtoconvert(result));
        }
        return null;
    }

    @Override
    public Object deleteOneMsg(String msgId) {
        SystemNews systemNews = systemNewsRspository.findById(msgId);
        TtpNews ttpNews = ttpNewsRepository.findByUserTtpId(msgId);
        InviteNews inviteNews = inviteNewsRepository.findById(msgId);

        if (systemNews != null) {
            systemNews.setHidden(1);
            return systemNewsRspository.save(systemNews);
        } else if(ttpNews != null ) {
            ttpNews.setHidden(1);
            return ttpNewsRepository.save(ttpNews);
        } else if(inviteNews != null) {
            inviteNews.setHidden(1);
            return inviteNewsRepository.save(inviteNews);
        }
        return null;
    }

    @Override
    public Object dingOneMsg(String msgId) {

        SystemNews systemNews = systemNewsRspository.findById(msgId);
        TtpNews ttpNews = ttpNewsRepository.findByUserTtpId(msgId);
        InviteNews inviteNews = inviteNewsRepository.findById(msgId);

        if (systemNews != null) {
            systemNews.setWeight(10);
            return systemNewsRspository.save(systemNews);
        } else if(ttpNews != null ) {
            ttpNews.setWeight(10);
            return ttpNewsRepository.save(ttpNews);
        } else if(inviteNews != null) {
            inviteNews.setWeight(10);
            return inviteNewsRepository.save(inviteNews);
        }
        return null;
    }
}
