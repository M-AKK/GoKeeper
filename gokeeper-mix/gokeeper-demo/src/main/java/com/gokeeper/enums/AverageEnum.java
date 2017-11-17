package com.gokeeper.enums;

import lombok.Getter;

/**
 * Created by Akk_Mac
 * Date: 2017/10/17 19:53
 */
@Getter
public enum AverageEnum implements CodeEnum{

    HIGH_AVERAGE(1, "高于平均水平"),
    EQUAL_AVERAGE(2, "达到平均水平"),
    LOW_AVERAGE(3, "低于平均水平"),
    ;

    private Integer code;

    private String message;

    AverageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
