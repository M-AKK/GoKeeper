package com.gokeeper.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Akk_Mac
 * Date: 2017/10/7 09:38
 */
@Data
public class JoinVo {

    private String username;

    private String userIcon;

    private String ttpId;

    private String ttpName;

    private String ttpType;

    private String createTime;

    private String startTime;

    private String finishTime;

    private String ttpTarget;

    private BigDecimal joinMoney;

    private BigDecimal allMoney;

    private Integer joinPeopleNums;

    private Integer leaveNotesNums;

    private String ifQuit;

    private String ifJoin;

}
