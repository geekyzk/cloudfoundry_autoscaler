package com.em248.cloudfoundry.service;


import com.em248.cloudfoundry.entity.Plan;

public interface PlanService {
	public Plan create(Plan plan);
	public boolean delete(String planId);
}
