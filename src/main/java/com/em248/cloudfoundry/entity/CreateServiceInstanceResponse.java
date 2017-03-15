package com.em248.cloudfoundry.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 * 创建服务实例请求返回类
 * @author tian
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class CreateServiceInstanceResponse {

	private ServiceInstance instance;
	
	public CreateServiceInstanceResponse() {}
	
	public CreateServiceInstanceResponse(ServiceInstance instance) {
		this.instance = instance;
	}

	@JsonSerialize
	@JsonProperty("dashboard_url")
	public String getDashboardUrl() {
		return instance.getDashboardUrl();
	}
	
}
