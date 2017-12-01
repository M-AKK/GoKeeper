package com.gokeeper.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: join的预览界面数据
 * @author: Created by Akk_Mac
 * @Date: 2017/11/19 10:11
 */
@Data
public class JoinPreVo {

    private String username;

    private String userIcon;

    private String ttpId;

    private String ttpName;

    private String ttpType;

    private String ttpTarget;

    private String createTime;

    private String StartTime;

    private BigDecimal allMoney;

    private Integer joinPeopleNums;
}
