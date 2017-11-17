package com.gokeeper.enums;

import lombok.Getter;

/**
 * Created by Akk_Mac
 * Date: 2017/10/6 10:23
 */
@Getter
public enum IfJoinEnum implements CodeEnum {

    YES(0, "允许中途加入"),
    NO(1, "不允许中途加入"),
    ;

    private Integer code;

    private String message;

    IfJoinEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
