package com.gokeeper.service;

import com.gokeeper.dataobject.UserTtp;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

import java.math.BigDecimal;

/**
 * @Description: 微信支付服务
 * @author: Created by Akk_Mac
 * @Date: 2017/11/21 19:53
 */
public interface WxPayService {

    /**
     * 创建支付
     * @param openId
     * @param orderAmount
     * @param orderId
     * @param orderName
     * @return PayResponse
     */
    PayResponse create(String openId, BigDecimal orderAmount, String orderId, String orderName);

    /**
     * 支付回调验证，告诉微信服务器已经支付成功
     * @param notifyData
     * @return
     */
    PayResponse notify(String notifyData);

    /**
     * 退款
     * @param userTtp
     * @param orderAmount
     * @return
     */
    RefundResponse refund(UserTtp userTtp, BigDecimal orderAmount);

}
