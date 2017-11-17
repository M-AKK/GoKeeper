package com.gokeeper.enums;

import lombok.Getter;

/**
 * Created by Akk_Mac
 * Date: 2017/10/6 10:11
 */
@Getter
public enum IfQuitEnum implements CodeEnum {

    YES(0, "允许中途退出"),
    NO(1, "不允许中途退出"),
    ;

    private Integer code;

    private String message;

    IfQuitEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
