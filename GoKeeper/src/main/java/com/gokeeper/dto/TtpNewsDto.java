package com.gokeeper.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 过滤掉一些不需要展示的字段，例如hidden
 * @author Created by Akk_Mac
 * @Date: 2017/10/21 11:38
 */
@Data
public class TtpNewsDto {

    private String id;

    private String ttpId;

    //消息类型
    private Integer newstype;

    //发起人name
    private String username;

    //发起人头像
    private String userIcon;

    private String newsname;

    //状态，已读(0),未读(1);
    private Integer newsstatus;

    private String previewText;

    //更新时间
    private Date updateTime;

    private Date startTime;

    private Date finishTime;

    private Integer weight;

    //是否完成
    private Integer ifFinish;

    private Integer finishnums;

    private Integer nofinishnums;

    private Integer leavesnums;

    private BigDecimal userDayBouns;

    private BigDecimal userTotalBouns;
}
