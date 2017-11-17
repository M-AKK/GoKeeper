package com.gokeeper.core.validate.code;

import java.time.LocalDateTime;

/**
@Description: 验证码的父类，这两个属性不管是图片还是短信都需要，所以用继承来写
@author: Created by Akk_Mac
@Date: Nov 12, 2017 5:15:59 PM
**/
public class ValidateCode {
	
	/**
	 * 随机数
	 */
	private String code; 
	
	/**
	 * 过期时间点
	 */
	private LocalDateTime expiretime;
	
	/**
	 * 构造的时候传递秒数，就是过期时间设置
	 * @param code
	 * @param expireIn
	 */
	public ValidateCode(String code, int expireIn){
		this.setCode(code);
		//生成过期时间点
		this.expiretime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public ValidateCode(String code, LocalDateTime expireTime){
		this.setCode(code);
		this.expiretime = expireTime;
	}
	
	public boolean isExpried() {
		return LocalDateTime.now().isAfter(expiretime);
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
