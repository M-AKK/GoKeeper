package com.gokeeper.service;

import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.vo.UserInfoVo;

/**
 * @author Created by Akk_Mac
 * Date: 2017/10/2 09:35
 */
public interface UserService {

    /**
     * 根据token获取UserId
     * @param token
     * @return
     */
    String getUserId(String token);

    /**
     * 根据手机号查找用户，发邀请时查找用户使用
     * @param phonenumber
     * @return
     */
    UserInfoVo getUserByphone(String phonenumber);


    UserInfo loginAndSave(UserInfo userInfo);
}
