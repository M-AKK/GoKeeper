package com.gokeeper.handler;

import com.gokeeper.core.validate.code.ValidateCodeException;
import com.gokeeper.exception.TTpException;
import com.gokeeper.utils.ResultVOUtil;
import com.gokeeper.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Created by Akk_Mac
 * Date: 2017/8/30 上午11:25
 */
@ControllerAdvice
@Slf4j
public class SellerExceptionHandler {

   /* @Autowired
    private ProjectUrlConfig projectUrlConfig;*/

    //拦截登录异常
    //登录界面地址：http://ynfywtq.hk1.mofasuidao.cn/sell/seller/login
    /*@ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"
        .concat(projectUrlConfig.getSell())
        .concat("/sell/seller/login"));
    }*/

    @ExceptionHandler(value = TTpException.class)
    @ResponseBody//直接在这里返回给前端
    //@ResponseStatus(HttpStatus.ACCEPTED)可以改变返回的status值,一些特殊项目可能需要(银行项目)
    public ResultVO handlerTtpException(TTpException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(value = ValidateCodeException.class)
    @ResponseBody//直接在这里返回给前端
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO handlerValidateCodeException(ValidateCodeException e) {
        log.info("handlerValidateCodeException调用成功："+e.getMessage());

        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
