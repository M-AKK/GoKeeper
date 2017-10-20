package com.gokeeper.repository;

import com.gokeeper.dataobject.TtpNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Akk_Mac
 * Date: 2017/10/18 18:36
 */
public interface TtpNewsRepository extends JpaRepository<TtpNews, String> {

    List<TtpNews> findAllByUserIdAndHiddenOrderByUpdateTimeDesc(String userId, Integer hidden);
}
