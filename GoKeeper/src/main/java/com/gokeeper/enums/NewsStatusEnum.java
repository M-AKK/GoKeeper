package com.gokeeper.enums;

import lombok.Getter;

/**
 * Created by Akk_Mac
 * Date: 2017/10/23 19:11
 */
@Getter
public enum NewsStatusEnum implements CodeEnum {

    NO_READ(0, "未读"),
    YES_READ(1, "已读"),
    ;

    private Integer code;

    private String message;

    NewsStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
