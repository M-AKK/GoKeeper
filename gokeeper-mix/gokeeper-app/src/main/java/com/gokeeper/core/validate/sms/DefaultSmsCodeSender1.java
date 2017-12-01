package com.gokeeper.core.validate.sms;

import com.gokeeper.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
@Description: 默认的短信发送方法并返回验证码
@author: Created by Akk_Mac
@Date: Nov 13, 2017 4:39:57 PM
**/
@Component("smsCodeSender")
@Slf4j
public class DefaultSmsCodeSender1 implements SmsCodeSender {

    @Autowired
    private SecurityProperties securityProperties;

    //随机数
    private static final String NONCE="123456";

	@Override
	public String send(String MOBILE, String code) throws Exception{

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(securityProperties.getCode().getSms().getServlerUrl());
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
        /*
         * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
         */
		String checkSum = CheckSumBuilder.getCheckSum(securityProperties.getCode().getSms().getAppSecret(), NONCE, curTime);

		// 设置请求的header
		httpPost.addHeader("AppKey", securityProperties.getCode().getSms().getAppKey());
		httpPost.addHeader("Nonce", NONCE);
		httpPost.addHeader("CurTime", curTime);
		httpPost.addHeader("CheckSum", checkSum);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		// 设置请求的的参数，requestBody参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        /*
         * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
         * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
         * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
         */
		nvps.add(new BasicNameValuePair("templateid", securityProperties.getCode().getSms().getTemplateid()));
		nvps.add(new BasicNameValuePair("mobile", MOBILE));
		nvps.add(new BasicNameValuePair("codeLen", Integer.toString(securityProperties.getCode().getSms().getLength())));

		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

		// 执行请求
		HttpResponse response = httpClient.execute(httpPost);
        /*
         * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
         * 2.具体的code有问题的可以参考官网的Code状态表
         */
		String result= EntityUtils.toString(response.getEntity(), "utf-8");

		JSONObject jsonObject = JSONObject.fromObject(result);
		log.info("【短信验证码返回状态：】result={}", result);
		String code1 = (String) jsonObject.get("obj");
		return code1;
	}
}
