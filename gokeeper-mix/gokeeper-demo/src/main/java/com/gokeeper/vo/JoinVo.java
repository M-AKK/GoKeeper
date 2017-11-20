package com.gokeeper.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by Akk_Mac
 * Date: 2017/10/7 09:38
 */
@Data
public class JoinVo extends JoinPreVo{

    private String startTime;

    private String finishTime;

    private BigDecimal joinMoney;

    private BigDecimal allMoney;

    private Integer joinPeopleNums;

    private Integer leaveNotesNums;

    private String ifQuit;

    private String ifJoin;

}
