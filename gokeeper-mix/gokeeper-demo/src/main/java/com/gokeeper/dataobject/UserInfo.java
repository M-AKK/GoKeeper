package com.gokeeper.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户信息列表，映射数据库的实体表
 * Created by Akk_Mac
 * Date: 2017/9/28 22:23
 */
@Entity
@Data
@DynamicUpdate
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
    private String qqOpenid;

    //用户名
    private String username;

    //密码
    private String password;

    //生日
    private String birthday;

    //性别，默认为男
    private Integer sex = 0;

    //所属地点
    private String city;

    //头像
    private String userIcon = "http://img5.imgtn.bdimg.com/it/u=1969967860,549604189&fm=27&gp=0.jpg";

    //用户等级
    private String level = "0级赏金猎人";

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}
