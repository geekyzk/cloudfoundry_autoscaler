package com.em248.cloudfoundry.repository;

import com.em248.cloudfoundry.entity.Plan;
import com.em248.cloudfoundry.entity.ServiceDefinition;
import org.springframework.data.repository.CrudRepository;


public interface PlanRepository extends CrudRepository<Plan, String> {
	public Long countByServiceDefinition(ServiceDefinition definition);
}
