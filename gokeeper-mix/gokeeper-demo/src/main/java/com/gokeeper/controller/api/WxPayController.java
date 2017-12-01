package com.gokeeper.controller.api;

import com.gokeeper.constant.UserInfoConstant;
import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.enums.FaqiTypeEnum;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.repository.TTpDetailRepository;
import com.gokeeper.repository.UserTtpRepository;
import com.gokeeper.service.WxPayService;
import com.gokeeper.utils.KeyUtil;
import com.gokeeper.utils.ResultVoUtil;
import com.gokeeper.vo.ResultVO;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 创建支付
 * @author Created by Akk_Mac
 * Date: 2017/8/25 下午4:13
 */
@Controller
@RequestMapping("/wxpay")
@Slf4j
public class WxPayController {

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private TTpDetailRepository tpDetailRepository;

    @Autowired
    private UserTtpRepository userTtpRepository;

    //支付创建：只需要接受orderId和回调地址
    @GetMapping("/create")
    public @ResponseBody ResultVO create(@RequestParam("ttpId") String ttpId,
                           /*@RequestParam("returnUrl") String returnUrl,*/
                           HttpServletRequest request) {
        //1. 根据传过来的ttpId查询Ttp详情
        TtpDetail ttpDetail = tpDetailRepository.findByTtpId(ttpId);
        if(ttpDetail == null){
            throw new TTpException(ResultEnum.ORDER_NOT_EXIST);
        }
        BigDecimal orderAmount = new BigDecimal(0);
        if(ttpDetail.getFaqiType().equals(FaqiTypeEnum.GROUP_TYPE.getCode())) {
            orderAmount = ttpDetail.getJoinMoney();
        } else {
            orderAmount = ttpDetail.getJoinSelfMoney();
        }
        //1.首先根据session获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        UserTtp userTtp = userTtpRepository.findByUserTtpId(user.getUserId()+ttpId);
        //2.修改userTtp的支付方式，退款时需要
        log.info("userid, enums, "+ user.getUserId()+"，"+BestPayTypeEnum.WXPAY_H5.getCode());
        userTtp.setPayType(BestPayTypeEnum.WXPAY_H5.getCode());
        //3.生成个orderId
        userTtp.setOrderId(KeyUtil.genUniqueKey());
        UserTtp updateResult = userTtpRepository.save(userTtp);
        if(updateResult == null){
            log.error("[创建支付] 更新失败， userTtp={}", userTtp);
            throw new TTpException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //4. 发起支付
        PayResponse payResponse = wxPayService.create(user.getWxOpenid(), orderAmount, userTtp.getOrderId(), ttpDetail.getTtpName());

        //用hbuilder的接口，只用返回payResponse就行了,也不用回调地址了
        return ResultVoUtil.success(payResponse);
    }

    /**
     * 微信异步通知
     * 入参是一段xml格式的字符串
     * @param notifyData
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {

        wxPayService.notify(notifyData);
        //返回给微信处理结果,否则会循环调用异步通知
        return new ModelAndView("pay/success");
    }




}
