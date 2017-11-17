package com.gokeeper.config;

import com.gokeeper.core.properties.SecurityConstants;
import com.gokeeper.core.properties.SecurityProperties;
import com.gokeeper.core.validate.code.ValidateCodeInterceptor;
import com.gokeeper.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @Description: 过滤器、拦截器的具体配置
 * @author: Created by Akk_Mac
 * @Date: 2017/11/17 14:58
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

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
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
        Set<String> urls = urlMap.keySet();
        //循环添加url到拦截地址
        for (String url : urls) {
            registry.addInterceptor(validateCodeInterceptor).addPathPatterns(url);
        }
        //注册拦截器
		registry.addInterceptor(validateCodeInterceptor);
    }



    /*@Bean
    public FilterRegistrationBean validateCodeFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        //把自己的Filter设置进spring管理发热Filter
        registrationBean.setFilter(validateCodeFilter);
        //配置具体过滤url,但我的配置写在Filter中
        //List<String> urls = new ArrayList<>();

        return registrationBean;
    }*/

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
