package com.gokeeper.service.impl;

import com.gokeeper.dataobject.InviteNews;
import com.gokeeper.dataobject.SystemNews;
import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.enums.NewsStatusEnum;
import com.gokeeper.enums.NewsTypeEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.repository.InviteNewsRepository;
import com.gokeeper.repository.SystemNewsRspository;
import com.gokeeper.repository.TTpDetailRepository;
import com.gokeeper.repository.UserInfoRepository;
import com.gokeeper.service.WebSocketService;
import com.gokeeper.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gokeeper.enums.ResultEnum.CHECK_USER;
import static com.gokeeper.enums.ResultEnum.TTP_MISS;

/**
 * @Description: websocket消息创建类
 * @author: Created by Akk_Mac
 * @Date: 2017/10/23 18:36
 */
@Service
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private TTpDetailRepository tTpDetailRepository;

    @Autowired
    private InviteNewsRepository inviteNewsRepository;

    @Autowired
    private SystemNewsRspository systemNewsRspository;

    /**
     * 创建一个邀请消息
     * @param ttpId 邀请的ttpId
     * @param userId 发起人userId
     * @return
     */
    @Override
    @Transactional//save的事务
    public InviteNews createInviteNews(String ttpId, String calluserId, String userId) {
        InviteNews result = new InviteNews();
        //获取消息的唯一id
        String uuid = KeyUtil.genUniqueKey();
        result.setId(uuid);
        result.setNewstype(NewsTypeEnum.INVITE.getCode());
        //1.首先根据发起人userId查找发起人信息
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        if(userInfo == null) {
            log.info("【查不到user信息】result={}");
            throw new TTpException(CHECK_USER);
        }
        result.setUserId(calluserId);
        //设置目标用户id
        result.setCallUserId(userId);
        result.setUsername(userInfo.getUsername());
        result.setUserIcon(userInfo.getUserIcon());

        //2.根据ttpId查找相应ttp需要展示的信息
        TtpDetail ttpDetail = tTpDetailRepository.findByTtpId(ttpId);
        if(ttpDetail == null) {
            log.info("【查不到ttp信息】result={}");
            throw new TTpException(TTP_MISS);
        }
        result.setNewsname(ttpDetail.getTtpName());

        //3.拼接预览消息
        String previewText = userInfo.getUsername()+"邀请您参加"+ttpDetail.getTtpName()+","+
                "开始时间：" + ttpDetail.getStartTime()+","+
                "参加金额："+ ttpDetail.getJoinMoney();
        result.setPreviewText(previewText);

        result.setNewsstatus(NewsStatusEnum.NO_READ.getCode());
        result.setHidden(1);
        result.setWeight(1);
        result.setTtpId(ttpId);

        InviteNews inviteNews = inviteNewsRepository.save(result);

        return inviteNews;
    }

    @Override
    @Transactional
    public SystemNews createSystemNews(UserInfo userInfo, String previewText, String text) {
        //获取消息的唯一id
        String uuid = KeyUtil.genUniqueKey();

        SystemNews systemNews = new SystemNews();
        systemNews.setId(uuid);
        systemNews.setUserId(userInfo.getUserId());
        systemNews.setUsername(userInfo.getUsername());
        systemNews.setUserIcon(userInfo.getUserIcon());
        systemNews.setNewstype(NewsTypeEnum.SYSTEM.getCode());
        systemNews.setNewsname("系统公告");
        systemNews.setNewsstatus(NewsStatusEnum.NO_READ.getCode());
        systemNews.setHidden(NewsStatusEnum.NO_READ.getCode());
        systemNews.setPreviewText(previewText);
        systemNews.setText(text);
        systemNews.setWeight(1);

        SystemNews result = systemNewsRspository.save(systemNews);

        return result;
    }
}
