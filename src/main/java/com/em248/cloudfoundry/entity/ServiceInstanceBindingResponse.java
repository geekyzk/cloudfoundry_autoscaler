package com.em248.cloudfoundry.entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *当绑定服务实例成功时的返回实体类
 * 
 * @author tian
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ServiceInstanceBindingResponse {

	@JsonSerialize
	@JsonProperty("credentials")
	private Map<String, String> credentials;
	
	@JsonProperty("syslog_drain_url")
	private String drainUrl;
	
	public ServiceInstanceBindingResponse() {}
	
	public ServiceInstanceBindingResponse(ServiceInstanceBinding binding) {
		this.credentials = binding.getCredentials();
		this.drainUrl = binding.getSyslogDrainUrl();
	}

	public Map<String, String> getCredentials() {
		return credentials;
	}

	public void setCredentials(Map<String, String> credentials) {
		this.credentials = credentials;
	}

	public String getDrainUrl() {
		return drainUrl;
	}

	public void setDrainUrl(String drainUrl) {
		this.drainUrl = drainUrl;
	}

	@Override
	public String toString() {
		return "ServiceInstanceBindingResponse [credentials=" + credentials + ", drainUrl=" + drainUrl + "]";
	}


	
	
}
