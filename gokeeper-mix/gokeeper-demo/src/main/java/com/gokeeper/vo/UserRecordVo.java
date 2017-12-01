package com.gokeeper.vo;

import lombok.Data;

/**
 * @author: Created by Akk_Mac
 * @Date: 2017/10/6 11:07
 */
@Data
public class UserRecordVo {

    /**
     * 第几天
     */
    private String days;

    /**
     * 完成状态
     */
    private Integer dayStatus;
}
