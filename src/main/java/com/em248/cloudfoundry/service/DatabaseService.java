package com.em248.cloudfoundry.service;


import com.em248.cloudfoundry.entity.ServiceInstance;
import com.em248.cloudfoundry.entity.ServiceInstanceBinding;

public interface DatabaseService {

	public boolean createDatabase(ServiceInstance instance);
	public boolean deleteDatabase(String serviceInstanceId);
	
	public boolean createUser(ServiceInstanceBinding binding);
	public boolean deleteUser(ServiceInstanceBinding binding);
}
