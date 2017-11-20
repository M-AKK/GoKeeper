package com.gokeeper.config;

import com.gokeeper.core.properties.SecurityProperties;
import com.gokeeper.core.validate.code.ValidateCodeInterceptor;
import com.gokeeper.core.validate.code.ValidateCodeType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 过滤器、拦截器的具体配置
 * @author: Created by Akk_Mac
 * @Date: 2017/11/17 14:58
 */
@Configuration
@Slf4j
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * 在拦截器注册前实例化Bean，就可以在拦截器里面用@Autowired了
     * @return
     */
    @Bean
    public ValidateCodeInterceptor getMyValidateCodeInterceptor(){
        return new ValidateCodeInterceptor();
    }

    @Autowired
    private ValidateCodeInterceptor validateCodeInterceptor;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //放入配置的url
        /*urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
        Set<String> urls = urlMap.keySet();
        StringBuffer sBuffer = new StringBuffer();
        //循环添加需要拦截的url到一个数组中
        for (String url : urls) {
            sBuffer.append(url+",");
        }
        log.info("注册的拦截地址："+sBuffer.substring(0, sBuffer.length()-1));
        registry.addInterceptor(getMyValidateCodeInterceptor()).addPathPatterns(sBuffer.substring(0, sBuffer.length()-1));*/
        registry.addInterceptor(validateCodeInterceptor);

        //注册拦截器
        //super.addInterceptors(registry);
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

    /*@Bean
    public FilterRegistrationBean validateCodeFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        //把自己的Filter设置进spring管理的Filter
        registrationBean.setFilter(validateCodeFilter);
        //配置具体过滤url,但我的配置写在Filter中
        //List<String> urls = new ArrayList<>();

        return registrationBean;
    }*/
}
