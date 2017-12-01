package com.gokeeper.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @author: Created by Akk_Mac
 * @Date: 2017/11/19 10:42
 */
@Data
public class GoPreVo {
    /**
     * 发起人name
     */
    private String userName;

    /**
     * 发起人头像
     */
    private String userIcon;

    private String ttpId;

    private String ttpName;

    /**
     * ttp状态，准备开始、进行中、完结
     */
    private Integer ttpStatus;

    /**
     * 用户获得的总奖金
     */
    private BigDecimal userTotalBouns;

    /**
     * 是否高于平均水平
     */
    private String highAverage;

    /**
     * 用户当天完成状态
     */
    private String userCurrentRecord;


}
