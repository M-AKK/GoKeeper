package com.gokeeper.enums;

import lombok.Getter;

/**
 * @author Created by Akk_Mac
 * @Date: 2017/10/22 15:51
 */
@Getter
public enum TtpStatusEnum implements CodeEnum {

    READY(1, "准备开始"),

    WORKING(2, "进行中"),

    FINISH(3, "完结"),
    ;

    private Integer code;

    private String message;

    TtpStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
