package com.gokeeper.core.properties;

import lombok.Data;

/**
@Description: 有验证码参数的获取类中间获取类，这里具体分别不同的验证码，比如可以把图片也放到这里
@author: Created by Akk_Mac
@Date: Nov 12, 2017 9:46:07 PM
**/
@Data
public class ValidateCodeProperties {

	private SmsCodeProperties sms = new SmsCodeProperties();



}
