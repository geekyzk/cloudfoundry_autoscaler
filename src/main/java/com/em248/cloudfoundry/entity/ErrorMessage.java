package com.em248.cloudfoundry.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 错误信息返回实体类
 * 
 * @author tian
 *
 */
public class ErrorMessage {

	@JsonProperty("message")
	private String message;

	public ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
