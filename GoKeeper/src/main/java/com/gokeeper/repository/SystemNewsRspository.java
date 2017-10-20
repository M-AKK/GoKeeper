package com.gokeeper.repository;

import com.gokeeper.dataobject.SystemNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Akk_Mac
 * Date: 2017/10/18 18:35
 */
public interface SystemNewsRspository extends JpaRepository<SystemNews, String> {

    //按是否隐藏查找系统消息
    List<SystemNews> findAllByUserIdAndHiddenOrderByUpdateTimeDesc(String userId, Integer hidden);

}
