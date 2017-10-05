package com.gokeeper.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


/**映射数据库的实体表
 * 用户信息列表
 * Created by Akk_Mac
 * Date: 2017/9/28 22:23
 */
@Entity
@Data
public class UserInfo {

    @Id
    private String userId;

    //微信openid
    private String wxOpenid;

    //手机号码
    private String phonenumber;

    //微博key
    private String weiboId;

    //QQid
    private String qqId;

    //用户名
    private String username;

    //密码
    private String password;

    //真实姓名
    private String realname;

    //性别
    private Integer sex;

    //头像
    private String userIcon;

}