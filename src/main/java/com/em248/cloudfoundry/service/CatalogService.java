package com.em248.cloudfoundry.service;

import com.em248.cloudfoundry.entity.ServiceDefinition;

import java.util.List;


public interface CatalogService {

	public ServiceDefinition createServiceDefinition(ServiceDefinition serviceDefinition);
	public boolean deleteServiceDefinition(String serviceDefinitionId);
	
	public List<ServiceDefinition> listServices();
	
	
}
