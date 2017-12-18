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

    //微信openid
    private String wxOpenid;

    //手机号码
    private String phonenumber;

    //微博key
    private String weiboId;

    //QQid
    private String qqOpenid;

    //用户名
    private String username;

    //生日
    private String birthday;

    //性别，默认为男
    private Integer sex = 0;

    //所属地点
    private String city;

    //头像
    private String userIcon;

    //用户等级
    private String level = "0级赏金猎人";
}
