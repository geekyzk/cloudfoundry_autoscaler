package com.em248.cloudfoundry.service;

import com.em248.cloudfoundry.entity.CreateServiceInstanceRequest;
import com.em248.cloudfoundry.entity.ServiceInstance;
import com.em248.cloudfoundry.entity.ServiceInstanceBinding;
import com.em248.cloudfoundry.entity.ServiceInstanceBindingRequest;

import java.util.List;


public interface ServiceManagement {
	
	public ServiceInstance createInstance(CreateServiceInstanceRequest serviceRequest);
	
	public boolean removeServiceInstance(String serviceInstanceId);
	
	public List<ServiceInstance> listInstances();
	
	public ServiceInstanceBinding createInstanceBinding(ServiceInstanceBindingRequest bindingRequest);
	
	public boolean removeBinding(String serviceBindingId);
	
	public List<ServiceInstanceBinding> listBindings();
	
}
