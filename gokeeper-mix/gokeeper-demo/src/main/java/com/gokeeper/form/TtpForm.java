package com.gokeeper.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * 前端传过来的创建TTP表单
 * Created by Akk_Mac
 * Date: 2017/10/1 10:23
 */
@Data
public class TtpForm {

    //ttp名称
    private String ttpName;

    //ttp目标(非必填，某些ttp才有)
    private String ttpTarget;

    //活动地点(非必填，某些活动才有)
    private String address;

    //请假条数量(非必填，某些活动才有)
    private Integer leaveNotesNums;

    //加入保险金额
    private BigDecimal joinMoney;

    //是否中途退出
    private Integer ifQuit;

    //是否中途加入
    private Integer ifJoin;

    //是否公开
    private Integer ifOpen;

    //扣减金额，为中途退出的扣减金额
    private Integer deductionRation;

    //ttp类型
    private Integer ttpType;

    //1.前端传来string类型，写一个工具类做转换
    private String startTime;

    //结束时间
    private String finishTime;

    //出资方式
    private Integer faqiType;

    //自己是否参加
    private Integer joinSelf;

    //独立出资时的发起金额
    private BigDecimal joinSelfMoney;

    /***************以下是人工监督类ttp特殊元素****************/
    //监督人userId
    private String supervisionperson;

    //被监督人userId
    private String besupervisionperson;


}
