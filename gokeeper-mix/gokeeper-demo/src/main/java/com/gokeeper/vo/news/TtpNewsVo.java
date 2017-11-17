package com.gokeeper.vo.news;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by Akk_Mac
 * @Date: 2017/10/18 21:26
 */
@Data
public class TtpNewsVo {

    private String id;

    private String ttpId;

    /**
     * ttp状态
     */
    private Integer ttpStatus;

    /**
     * 消息类型
     */
    private Integer newstype;

    /**
     * 发起人name
     */
    private String username;

    /**
     * 发起人头像
     */
    private String userIcon;

    private String newsname;

    /**
     * 状态，已读(0),未读(1);
     */
    private Integer newsstatus;

    private String previewText;

    /**
     * 更新时间
     */
    private String updateTime;

    private Integer weight;

    private String starttime;

    private String finishtime;

    /**
     * 是否完成
     */
    private Integer ifFinish;

    private Integer finishnums;

    private Integer nofinishnums;

    private Integer leavesnums;

    private BigDecimal userDayBouns;

    private BigDecimal userTotalBouns;
}
