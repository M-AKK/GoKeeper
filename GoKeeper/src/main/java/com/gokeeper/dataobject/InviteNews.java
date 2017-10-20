package com.gokeeper.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Akk_Mac
 * Date: 2017/10/13 18:44
 */
@Entity
@Data
@DynamicUpdate
public class InviteNews {

    //消息id
    @Id
    private String id;

    //消息拥有者id
    private String userId;

    //消息类型
    private Integer newstype;

    //发起人name
    private String username;

    //发起人头像
    private String userIcon;

    private String newsname;

    //状态，已读(0),未读(1);
    private Integer newsstatus;

    //是否隐藏，0为公开，1为隐藏
    private Integer hidden;

    //预览消息
    private String preview_text;

    private String ttpId;//此邀请ttp的Id

    private String payUrl;//支付页面的直接跳转

    //更新时间
    private Date updateTime;

    //权重
    private Integer weight;


}
