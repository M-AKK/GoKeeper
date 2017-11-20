package com.gokeeper.repository;

import com.gokeeper.dataobject.InviteNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Akk_Mac
 * Date: 2017/10/13 18:43
 */
public interface InviteNewsRepository extends JpaRepository<InviteNews, String> {

    /**
     * 根据用户id获取此用户拥有的所有消息
     * @param userId
     * @return
     */
    List<InviteNews> findAllByUserIdAndHiddenOrderByUpdateTimeDesc(String userId, Integer hidden);

    /**
     * 查找某一条邀请消息
     * @param id
     * @return
     */
    InviteNews findById(String id);
}
