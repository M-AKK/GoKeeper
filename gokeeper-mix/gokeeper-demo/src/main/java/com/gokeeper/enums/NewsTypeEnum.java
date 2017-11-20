package com.gokeeper.enums;

import lombok.Getter;

/**
 * @Description:
 * @author: Created by Akk_Mac
 * @Date: 2017/11/20 14:42
 */
@Getter
public enum NewsTypeEnum implements CodeEnum {

    SYSTEM(1, "系统消息"),
    TTP(2, "ttp消息"),
    INVITE(3,"邀请消息"),
    ;

    private Integer code;

    private String message;

    NewsTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
