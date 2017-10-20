package com.gokeeper.VO.news;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Akk_Mac
 * Date: 2017/10/18 21:26
 */
@Data
public class TtpNewsVo {

    private String id;

    //消息类型
    private Integer newstype;

    //发起人name
    private String username;

    //发起人头像
    private String userIcon;

    private String newsname;

    //状态，已读(0),未读(1);
    private Integer newsstatus;

    //更新时间
    private String updateTime;

    private Integer weight;

    //是否完成
    private Integer ifFinish;

    private Integer finishnums;

    private Integer nofinishnums;

    private Integer leavesnums;

    private BigDecimal userDayBouns;

    private BigDecimal userTotalBouns;
}
