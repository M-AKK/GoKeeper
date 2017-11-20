package com.gokeeper.repository;

import com.gokeeper.dataobject.SystemNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Created by Akk_Mac
 * Date: 2017/10/18 18:35
 */
public interface SystemNewsRspository extends JpaRepository<SystemNews, String> {

    /**
     * 按是否隐藏查找系统消息，系统消息应该是全都查找出来
     * @param hidden
     * @return List<SystemNews>
     */
    List<SystemNews> findAllByHiddenOrderByUpdateTimeDesc(Integer hidden);

    /**
     * 根据id查询一条msg
     * @param msgId
     * @return
     */
    SystemNews findById(String msgId);

}
