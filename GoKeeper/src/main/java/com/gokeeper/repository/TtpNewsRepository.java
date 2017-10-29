package com.gokeeper.repository;

import com.gokeeper.dataobject.TtpNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Created by Akk_Mac
 * @Date: 2017/10/18 18:36
 */
public interface TtpNewsRepository extends JpaRepository<TtpNews, String> {

    /**
     * 查找所有ttp消息并根据更新时间排序
     * @param userId
     * @param hidden
     * @return
     */
    List<TtpNews> findAllByUserIdAndHiddenOrderByUpdateTimeDesc(String userId, Integer hidden);

    /**
     * 查找所有此ttp的消息
     * @param ttpId
     * @return
     */
    List<TtpNews> findByTtpId(String ttpId);
}
