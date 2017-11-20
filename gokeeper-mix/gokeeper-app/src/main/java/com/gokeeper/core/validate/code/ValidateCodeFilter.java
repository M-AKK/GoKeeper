
package com.gokeeper.core.validate.code;

import com.gokeeper.core.properties.SecurityConstants;
import com.gokeeper.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author zhailiang
 *
 */

/*@Component("validateCodeFilter")*/
@Slf4j
public class ValidateCodeFilter implements Filter /*extends OncePerRequestFilter implements InitializingBean*/  {

    /*@Autowired
    private ValidateCodeExceptionHandler validateCodeExceptionHandler;*/

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

    /**
	 * 存放所有需要校验验证码的url
	 */

	private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
	 * 验证请求url与配置的url是否匹配的工具类
	 */

	private AntPathMatcher pathMatcher = new AntPathMatcher();


    /**
	 * 初始化要拦截的url配置信息
	 */


/*  @Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		log.info("首先执行放入url操作");
        //这里ValidateCodeType.SMS就等于大写的SMS，并没有调用其中的方法
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
		//自己配置的短信拦截url
		addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
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

	//初始化
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        ServletContext context = request.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        SecurityProperties securityProperties = ctx.getBean(SecurityProperties.class);
        ValidateCodeProcessorHolder validateCodeProcessorHolder = ctx.getBean(ValidateCodeProcessorHolder.class);
        //ValidateCodeExceptionHandler validateCodeExceptionHandler = ctx.getBean(ValidateCodeExceptionHandler.class);

        //这里ValidateCodeType.SMS就等于大写的SMS，并没有调用其中的方法
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
		ValidateCodeType type = getValidateCodeType(req);
		if (type != null) {
			log.info("校验请求(" + req.getRequestURI() + ")中的验证码,验证码类型" + type);
			try {
				validateCodeProcessorHolder.findValidateCodeProcessor(type)
						.validate(new ServletWebRequest(req, resp));
				log.info("验证码校验通过");
			} catch (ValidateCodeException exception) {
			    //注入handler来捕获，否则throw Exception在filter是捕获不道德，handler只能捕获Controller的异常。
                throw new ValidateCodeException(exception.getCode(), exception.getMessage());
                //validateCodeExceptionHandler.handlerValidateCodeException(exception);
                //return;
			}
		}

		chain.doFilter(request, response);

	}

    @Override
    public void destroy() {
        System.out.println("time filter destroy");
    }


    /**
	 * 获取校验码的类型，如果当前请求不需要校验，则返回null
	 * 
	 * @param request
	 * @return
	 */

	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		ValidateCodeType result = null;
		if (StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
                //log.info("map里面的url："+url);
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}

}

