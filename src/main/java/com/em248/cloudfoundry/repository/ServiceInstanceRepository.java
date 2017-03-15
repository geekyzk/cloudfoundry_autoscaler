package com.em248.cloudfoundry.repository;

import com.em248.cloudfoundry.entity.ServiceInstance;
import org.springframework.data.repository.CrudRepository;


public interface ServiceInstanceRepository extends CrudRepository<ServiceInstance, String> {
	
	public Long countByPlanId(String planId);
	
}
