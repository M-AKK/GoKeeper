package com.gokeeper.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ttp详情表，也是创建表
 * Created by Akk_Mac
 * Date: 2017/10/1 21:19
 */
@Entity
@Data
public class TtpDetail {

    //ttpid
    @Id
    private String ttpId;

    //userid
    private String userId;

    //ttp名称
    private String ttpName;

    //活动目标。某些ttp才有
    private Double ttpTarget;

    //请假条数量，默认为0
    private Integer leaveNotesNums;

    private String address;

    //加入金额
    private BigDecimal joinMoney;

    //ttp的总奖金
    private BigDecimal allMoney;

    //是否允许中途退出，0是不允许，1允许
    private Integer ifQuit;

    //是否允许中途加入
    private Integer ifJoin;

    //是否公开
    private Integer ifOpen;

    //扣减比例，默认为20(前端要判断为整数)
    private Integer deductionRation;

    //ttp类型
    private Integer ttpType;

    //ttp状态(0,准备开始；1，进行中；2，完结)
    private Integer ttpStatus;

    private Date createTime;

    //ttp开始时间
    private Date startTime;

    //ttp结束时间
    private Date finishTime;

}
