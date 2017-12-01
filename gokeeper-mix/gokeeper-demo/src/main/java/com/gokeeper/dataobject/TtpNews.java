package com.gokeeper.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by Akk_Mac
 * @Date: 2017/10/18 12:18
 */
@Data
@Entity
@DynamicUpdate
public class TtpNews {

    //消息id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String userTtpId;

    private String ttpId;

    /**
     * 消息拥有者id
     */
    private String userId;

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
     * 是否隐藏，0为公开，1为隐藏
     */
    private Integer hidden;

    /**
     * 更新时间
     */
    private Date updateTime;

    private Date startTime;

    private Date finishTime;

    /**
     * 是否完成
     */
    private Integer ifFinish;

    private Integer finishnums;

    private Integer nofinishnums;

    //剩余假条
    private Integer leaveNotes;

    private Integer leavenums;

    private BigDecimal userDayBouns;

    private BigDecimal userTotalBouns;

    private Integer weight;
}
