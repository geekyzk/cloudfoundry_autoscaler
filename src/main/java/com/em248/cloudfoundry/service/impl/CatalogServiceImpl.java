package com.em248.cloudfoundry.service.impl;

import java.util.List;

import com.em248.cloudfoundry.entity.ServiceDefinition;
import com.em248.cloudfoundry.repository.PlanRepository;
import com.em248.cloudfoundry.repository.ServiceDefinitionRepository;
import com.em248.cloudfoundry.service.BaseService;
import com.em248.cloudfoundry.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tian
 */
@Service
public class CatalogServiceImpl extends BaseService implements CatalogService {

	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private ServiceDefinitionRepository serviceRepository;


	@Override
	public ServiceDefinition createServiceDefinition(ServiceDefinition serviceDefinition) {
		return serviceRepository.save(serviceDefinition);
	}

	@Override
	public List<ServiceDefinition> listServices() {
		return makeCollection(serviceRepository.findAll());
	}

	@Override
	public boolean deleteServiceDefinition(String serviceDefinitionId) {
		if(!serviceRepository.exists(serviceDefinitionId)){
			return false;
		}
		ServiceDefinition serviceDefinition = serviceRepository.findOne(serviceDefinitionId);
		if(planRepository.countByServiceDefinition(serviceDefinition) > 0){
			throw new IllegalStateException("Can not remove service instance, the instance has plans associated to it");
		}
		serviceRepository.delete(serviceDefinitionId);
		return true;
	}

	
	
	
}
