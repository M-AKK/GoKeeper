package com.gokeeper.service;

import com.gokeeper.dataobject.InviteNews;
import com.gokeeper.dataobject.SystemNews;
import com.gokeeper.dataobject.UserInfo;

/**
 * websocket模板消息的service，包括所有的websocket通信
 * Created by Akk_Mac
 * Date: 2017/10/23 18:33
 */
public interface WebSocketService {

    /**
     * @param ttpId 邀请的ttpId
     * @param userId 发起人userId
     * @return
     */
    InviteNews createInviteNews(String ttpId, String userId);

    SystemNews createSystemNews(UserInfo userInfo, String previewText, String text);

}
