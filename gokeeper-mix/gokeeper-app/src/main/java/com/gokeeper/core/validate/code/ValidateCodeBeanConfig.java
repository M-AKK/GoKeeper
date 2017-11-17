package com.gokeeper.core.validate.code;

import com.gokeeper.core.validate.sms.DefaultSmsCodeSender;
import com.gokeeper.core.validate.sms.DefaultSmsCodeSender1;
import com.gokeeper.core.validate.sms.SmsCodeGenerator;
import com.gokeeper.core.validate.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 @Description: 把逻辑配置化配置化，可以方便第三方直接覆盖
 这是一个配置类，会放到spring容器里
 @author: Created by Akk_Mac
 @Date: Nov 13, 2017 12:18:33 PM
 **/
@Configuration
public class ValidateCodeBeanConfig {


    /**
     * 首先查看系统中别的地方有没有类注解了smsCodeGenerator，没有的话再来调用这个里面的
     * @return
     */
    /*@Bean
    @ConditionalOnMissingBean(name= "smsCodeGenerator")
    public ValidateCodeGenerator smsCodeGenerator() {

        return new SmsCodeGenerator();
    }*/

	/**
	 * 这是另一种语法：接口名SmsCodeSender.class，系统就会去找这个接口的实现，没有才调用这个默认的
	 * 这里的name只能是继承了SmsCodeSender类的@Component的一样，否则在扫描的时候发现继承的是别的类就会报错
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(name = "smsCodeSender")
	public SmsCodeSender smsCodeSender() {
		//这里是直接返回默认的发送实现类
		return new DefaultSmsCodeSender();
	}
}

