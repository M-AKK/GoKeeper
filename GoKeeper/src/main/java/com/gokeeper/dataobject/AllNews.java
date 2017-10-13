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
public class AllNews {

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

    //专门存放系统消息的字段
    private String content;

    //存放昨日日期
    private Date days;

    //完成人数
    private Integer finishnums;

    //未完成人数
    private Integer nofinishnums;

    //请假的人数，推算出
    private Integer leavenums;

    //用户日奖金变化
    private BigDecimal userDayBouns;

    //用户总奖金
    private BigDecimal userTotalBouns;

    //更新时间
    private Date updateTime;
}
