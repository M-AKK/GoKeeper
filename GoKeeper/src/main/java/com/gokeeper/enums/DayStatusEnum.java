package com.gokeeper.enums;

import lombok.Getter;

/**
 * Created by Akk_Mac
 * Date: 2017/10/6 11:20
 */
@Getter
public enum DayStatusEnum implements CodeEnum {

    FINIS(1, "完成"),
    NO_FINISH(0, "未完成"),
    QINGJIA(2, "请假"),
    ;

    private Integer code;

    private String message;

    DayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
