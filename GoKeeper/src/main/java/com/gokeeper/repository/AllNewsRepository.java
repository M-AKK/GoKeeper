package com.gokeeper.repository;

import com.gokeeper.dataobject.AllNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Akk_Mac
 * Date: 2017/10/13 18:43
 */
public interface AllNewsRepository extends JpaRepository<AllNews, String> {

    /**
     * 根据用户id获取此用户拥有的所有消息
     * @param userId
     * @return
     */
    List<AllNews> findAllByUserId(String userId);
}
