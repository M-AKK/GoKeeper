package com.gokeeper.service;

import com.gokeeper.vo.news.AllNewsVo;

import java.util.List;

/**
 * @author Created by Akk_Mac
 * Date: 2017/10/13 19:25
 */
public interface AllNewsService {

    /**
     * 查找所有非隐藏消息
     * @param userId
     * @return
     */
    List<AllNewsVo> findAllOpenMsg(String userId, Integer hidden);

    /**
     * 获取一条消息
     * @param msgId
     * @return
     */
    Object getOneMsg(String msgId);

    /**
     * 删除一条消息
     * @param msgId
     * @return
     */
    Object deleteOneMsg(String msgId);

    /**
     * 置顶一条消息
     * @param msgId
     * @return
     */
    Object dingOneMsg(String msgId);


}
