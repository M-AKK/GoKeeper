package com.gokeeper.core.properties;

import lombok.Data;

/**
@Description: 图片验证码的底层参数类就是短信验证码的底层属性
@author: Created by Akk_Mac
@Date: Nov 12, 2017 9:41:53 PM
**/
@Data
public class SmsCodeProperties {
	
	private int length = 6;
	private int expireIn = 60;
	/**
	 * 要处理的url
	 */
	private String url = "";

    /**
     * 发送验证码的请求路径URL
     */
    private String servlerUrl;

    private String appKey;

    private String appSecret;

    private String templateid;
}
