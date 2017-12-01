package com.gokeeper.enums;

import lombok.Getter;

/**
 * @Description:
 * @author: Created by Akk_Mac
 * @Date: 2017/11/21 10:53
 */
@Getter
public enum JoinSelfEnum implements CodeEnum {

    YES(1, "自己参加"),
    NO(0, "自己不参加"),
    ;

    private Integer code;

    private String message;

    JoinSelfEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}