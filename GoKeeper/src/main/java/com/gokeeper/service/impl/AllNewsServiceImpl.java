package com.gokeeper.service.impl;

import com.gokeeper.VO.news.AllNewsVo;
import com.gokeeper.VO.news.SystemNewsVo;
import com.gokeeper.dataobject.InviteNews;
import com.gokeeper.dataobject.SystemNews;
import com.gokeeper.dataobject.TtpNews;
import com.gokeeper.repository.InviteNewsRepository;
import com.gokeeper.repository.SystemNewsRspository;
import com.gokeeper.repository.TtpNewsRepository;
import com.gokeeper.service.AllNewsService;
import com.gokeeper.utils.Converter.NewsBeanZNewsDtoConverter;
import com.gokeeper.utils.Converter.NewsDtoZNewsVoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.gokeeper.utils.DateUtil.dateFormat2;

/**
 * 信息界面初始化列表
 * Created by Akk_Mac
 * Date: 2017/10/13 19:26
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
    public AllNewsVo findAllOpenMsg(String userId, Integer hidden) {

        AllNewsVo allNewsVo = new AllNewsVo();
        //1.转化系统消息到Vo对象
        List<SystemNews> systemNewsList = systemNewsRspository.findAllByUserIdAndHiddenOrderByUpdateTimeDesc(userId, hidden);
        List<TtpNews> ttpNewsList = ttpNewsRepository.findAllByUserIdAndHiddenOrderByUpdateTimeDesc(userId, hidden);
        List<InviteNews> inviteNewsList = inviteNewsRepository.findAllByUserIdAndHiddenOrderByUpdateTimeDesc(userId, hidden);
        //2.转化ttp消息到Vo
        //3.转化邀请消息到对象
        allNewsVo.setSystemNewsList(NewsDtoZNewsVoConverter.SystemNewsDtoZVoconvert(NewsBeanZNewsDtoConverter.SystemNewsZDtoconvert(systemNewsList)));
        allNewsVo.setTtpNewsList(NewsDtoZNewsVoConverter.TtpNewsDtoZVoconvert(NewsBeanZNewsDtoConverter.TtpNewsZDtoconvert(ttpNewsList)));
        allNewsVo.setInviteNewsList(NewsDtoZNewsVoConverter.InviteNewsDtoZVoconvert(NewsBeanZNewsDtoConverter.InviteNewsZDtoconvert(inviteNewsList)));

        return allNewsVo;
    }


}
