package com.em248.cloudfoundry.service.impl;

import com.em248.cloudfoundry.entity.Plan;
import com.em248.cloudfoundry.entity.ServiceDefinition;
import com.em248.cloudfoundry.repository.PlanRepository;
import com.em248.cloudfoundry.repository.ServiceDefinitionRepository;
import com.em248.cloudfoundry.repository.ServiceInstanceRepository;
import com.em248.cloudfoundry.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author tian
 */
@Service
public class PlanServiceImpl implements PlanService {
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private ServiceDefinitionRepository serviceRepository;
	
	@Autowired
	private ServiceInstanceRepository instanceRepository;

	
	@Override
	public Plan create(Plan plan) {
		ServiceDefinition serviceDefinition = serviceRepository.findOne(plan.getServiceDefinition().getId());
		if(serviceDefinition == null){
			throw new IllegalArgumentException("No such service definition : " + plan.getServiceDefinition().getId());
		}
		plan.setServiceDefinition(serviceDefinition);
		plan.getMetadata().setId(plan.getId());
		return planRepository.save(plan);
	}

	@Override
	public boolean delete(String planId) {
		Plan plan = planRepository.findOne(planId);
		if(plan == null){
			return false;
		}
		if(instanceRepository.countByPlanId(planId) > 0){
			throw new IllegalStateException("Can not remove plan, it's being used by service instances");
		}
		planRepository.delete(plan);
		return true;
	}

}
