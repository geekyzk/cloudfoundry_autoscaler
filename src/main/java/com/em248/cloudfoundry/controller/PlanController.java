package com.em248.cloudfoundry.controller;

import com.em248.cloudfoundry.entity.Plan;
import com.em248.cloudfoundry.entity.ServiceDefinition;
import com.em248.cloudfoundry.service.PlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author tian
 * 操作plan相关
 */
@Controller
@RequestMapping(value="/v2/catalog/services/{sid}/plans")
public class PlanController {
	
	@Autowired
	private PlanService service;
	
	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);


	/**
	 * 创建一个plan
	 * @param plan
	 * @param sid
	 * @return
	 */
	@ResponseBody
	@PostMapping(consumes="application/json", produces="application/json")
	public ResponseEntity<Plan> createPlan(@RequestBody Plan plan,
										   @PathVariable("sid") String sid){
		ServiceDefinition serviceDefinition = new ServiceDefinition(sid);
		plan.setServiceDefinition(serviceDefinition);
		Plan persistedPlan = service.create(plan);
		ResponseEntity<Plan> response= new ResponseEntity<>(persistedPlan, HttpStatus.CREATED);
		return response;
	}
	
	@DeleteMapping(value="/{planId}",produces="application/json")
	public ResponseEntity<String> deletePlan(@PathVariable("sid") String sid,
											 @PathVariable("planId") String planId){
		boolean deleted = service.delete(planId);
		HttpStatus status = deleted ? HttpStatus.OK : HttpStatus.GONE;
		ResponseEntity<String> response = new ResponseEntity<>("{}",status);  
		return response;
	}
	
	
	
	
}
