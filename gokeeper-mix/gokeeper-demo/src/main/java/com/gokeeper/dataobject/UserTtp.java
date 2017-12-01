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
 * ttp和user关联实体表
 * @author Created by Akk_Mac
 * Date: 2017/10/3 11:22
 */
@Entity
@Data
//动态更新，只要数据发生变化了，数据库的时间也会变
@DynamicUpdate
public class UserTtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userTtpId;

    private String userId;

    private String ttpId;

    /**
     * 用户此ttp每日奖金，突然发现只用显示总奖金就行了
     */
    private BigDecimal userDayBouns;

    /**
     * 用户此ttp总奖金
     */
    private BigDecimal userTotalBouns;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 用户对应此ttp的状态
     */
    private Integer userTtpStatus;

    //假条使用数量
    private Integer leaveNotes = 0;

    private Integer ttpSchedule;

    private Date updateTime;
}
