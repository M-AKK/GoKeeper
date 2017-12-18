package com.gokeeper.service;

import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.vo.UserInfoVo;

import java.util.List;

/**
 * @author Created by Akk_Mac
 * Date: 2017/10/2 09:35
 */
public interface UserService {

    /**
     * 根据手机号查找用户，发邀请时查找用户使用
     * @param searchmap
     * @return
     */
    List<UserInfoVo> getUserByphone(String searchmap);

    /**
     * 根据userID查找用户
     * @param userId
     * @return
     */
    UserInfoVo getuserById(String userId);

    /**
     * 登录判断和保存用户
     * @param userInfo
     * @return
     */
    UserInfo loginAndSave(UserInfo userInfo);

    /**
     * 修改并保存用户信息
     * @param userInfo
     * @return
     */
    UserInfo modifyUser(UserInfo userInfo);
}
