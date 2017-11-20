package com.gokeeper.core.validate.code;

import com.gokeeper.core.properties.SecurityConstants;
import com.gokeeper.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 验证码判断拦截器，由于Filter不能用属性注入和捕获异常，捕获了还不能正常返回，只能用Interceptor来代替
 * @author: Created by Akk_Mac
 * @Date: 2017/11/17 20:41
 */
//这是拦截所有url
@Component
@Slf4j
public class ValidateCodeInterceptor implements HandlerInterceptor {

    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
        ValidateCodeType type = urlMap.get(httpServletRequest.getRequestURI());
        log.info("type是不是null？"+type+"URI"+httpServletRequest.getRequestURI());
        if(type != null) {
            log.info("校验请求(" + httpServletRequest.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(httpServletRequest, httpServletResponse));
                log.info("验证码校验通过");
                return true;
            } catch (ValidateCodeException exception) {
                //注入handler来捕获，否则throw Exception在filter是捕获不到的，handler只能捕获Controller的异常。
                throw new ValidateCodeException(exception.getCode(), exception.getMessage());
            }
        }
        //要继续调用controller处理器的方法
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }
}
