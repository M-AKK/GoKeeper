package com.gokeeper.enums;

import lombok.Getter;

/**
 * @author Created by Akk_Mac
 * Date: 2017/10/6 10:24
 */
@Getter
public enum IfOpenEnum implements CodeEnum {

    YES(1, "公开"),
    NO(0, "不公开"),
    ;

    private Integer code;

    private String message;

    IfOpenEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
