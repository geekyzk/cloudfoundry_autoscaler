package com.em248.cloudfoundry.repository;

import com.em248.cloudfoundry.entity.ServiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance, String> {

    public Long countByPlanId(String planId);
	
}
