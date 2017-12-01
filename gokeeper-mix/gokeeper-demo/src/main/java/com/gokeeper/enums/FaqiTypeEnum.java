package com.gokeeper.enums;

import lombok.Getter;

/**
 * @Description: 发起ttp的方式
 * @author: Created by Akk_Mac
 * @Date: 2017/11/21 10:49
 */
@Getter
public enum FaqiTypeEnum implements CodeEnum {

    GROUP_TYPE(1, "集体出资"),
    INDEPENDENT(2, "独立出资"),
    ;

    private Integer code;

    private String message;

    FaqiTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
