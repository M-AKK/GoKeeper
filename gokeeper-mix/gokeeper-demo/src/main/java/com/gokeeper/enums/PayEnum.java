package com.gokeeper.enums;

import lombok.Getter;

/**
 * @Description:
 * @author: Created by Akk_Mac
 * @Date: 2017/11/21 17:25
 */
@Getter
public enum PayEnum implements CodeEnum {

    NO(0, "未支付"),

    YES(1, "已支付"),
    ;

    private Integer code;

    private String message;

    PayEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}