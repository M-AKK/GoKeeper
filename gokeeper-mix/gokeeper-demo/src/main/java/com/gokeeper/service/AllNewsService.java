package com.gokeeper.service;

import com.gokeeper.vo.news.AllNewsVo;

/**
 * Created by Akk_Mac
 * Date: 2017/10/13 19:25
 */
public interface AllNewsService {

    /**
     * 查找所有非隐藏消息
     * @param userId
     * @return
     */
    AllNewsVo findAllOpenMsg(String userId, Integer hidden);


}
