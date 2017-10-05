package com.gokeeper.repository;

import com.gokeeper.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户登录Dao
 * Created by Akk_Mac
 * Date: 2017/9/28 23:12
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    //按用户名查找
    UserInfo findByUsername(String username);
}
