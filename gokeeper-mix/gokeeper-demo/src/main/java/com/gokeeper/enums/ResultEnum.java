package com.gokeeper.enums;

import lombok.Getter;

/**
 * @Description: 返给前端的提示信息
 * @author:Created by KHM
 * @Data: 2017/7/27 17:35
 */
@Getter
public enum ResultEnum implements CodeEnum {

    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数不正确"),
    CREAT_USER(2, "登录失败"),

    TOKEN_MISS(2,"session失效，重新登录"),
    CREATE_ERROR(2003,"创建活动失败，请重新创建"),
    TTP_MISS(2001, "查找不到此ttp"),
    USER_ERROR(1001, "用户信息错误"),
    CHECK_USER(1002, "查找不到该用户"),
    USER_REPEAT(1003, "此手机号被多用户使用"),
    USER_JOIN_REPEAT(1004, "用户已经参加ttp,不能重复加入"),
    USER_MODIFY_ERROR(1005, "用户保存失败"),
    DATE_ERROE(3, "日期转换出错"),


    ORDER_NOT_EXIST(10, "TTP不存在"),
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(11, "微信支付异步通知金额校验不通过"),
    ORDER_STATUS_ERROR(12, "TTP状态不正确"),
    ORDER_PAY_STATUS_ERROR(13, "TTP支付状态出错"),
    ORDER_UPDATE_FAIL(14, "TTP更新失败"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
