package com.gokeeper.service;

import com.gokeeper.dataobject.AllNews;
import com.gokeeper.dataobject.UserTtp;

import java.util.List;

/**
 * Created by Akk_Mac
 * Date: 2017/10/13 19:25
 */
public interface AllNewsService {

    /**
     * 根据用户id获取此用户拥有的所有消息
     * @param userId
     * @return
     */
    List<AllNews> findAllByUserId(String userId);


}
