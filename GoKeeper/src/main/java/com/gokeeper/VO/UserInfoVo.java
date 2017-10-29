package com.gokeeper.vo;

import lombok.Data;

/**
 * userInfoVo对象，查询好友时需要
 * Created by Akk_Mac
 * Date: 2017/10/22 17:21
 */
@Data
public class UserInfoVo {

    private String userId;

    //手机号码
    private String phonenumber;

    //用户名
    private String username;

    //真实姓名
    private String realname;

    //性别
    private Integer sex;

    //头像
    private String userIcon;
}
