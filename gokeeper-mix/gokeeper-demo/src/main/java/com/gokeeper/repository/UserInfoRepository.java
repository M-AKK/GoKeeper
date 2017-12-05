package com.gokeeper.repository;

import com.gokeeper.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 用户登录Dao
 * @author: Created by Akk_Mac
 * @Date: 2017/9/28 23:12
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    /**
     * 按用户名查找
     * @param username
     * @return
     */
    UserInfo findByUsername(String username);

    /**
     * 按userId查找
     * @param userId
     * @return
     */
    UserInfo findByUserId(String userId);

    /**
     * 模糊查找
     * @param searchmap
     * @return
     */
    @Query(value = "SELECT * FROM user_info u WHERE u.phonenumber LIKE CONCAT('%',:searchmap,'%') OR u.username LIKE CONCAT('%',:searchmap,'%') limit 1", nativeQuery = true)
    UserInfo findByPhonenumber(@Param("searchmap") String searchmap);

    /**
     * 按QQid查找
     * @param qqOpenid
     * @return
     */
    UserInfo findByQqOpenid(String qqOpenid);

    /**
     * 微信的openid
     * @param wxOpenid
     * @return
     */
    UserInfo findByWxOpenid(String wxOpenid);
}
