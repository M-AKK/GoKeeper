package com.gokeeper.enums;

import lombok.Getter;

/**
 * @Description:
 * @author: Created by Akk_Mac
 * @Date: 2017/11/21 17:22
 */
@Getter
public enum UserTtpStatusEnum implements CodeEnum {

    READY(1, "准备开始"),

    WORKING(2, "进行中"),

    FINISH(3, "完结"),

    QUIT(4, "中途退出"),
    ;

    private Integer code;

    private String message;

    UserTtpStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
