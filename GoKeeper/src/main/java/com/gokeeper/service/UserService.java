package com.gokeeper.service;

/**
 * Created by Akk_Mac
 * Date: 2017/10/2 09:35
 */
public interface UserService {

    //根据token获取UserId
    String getUserId(String token);
}
