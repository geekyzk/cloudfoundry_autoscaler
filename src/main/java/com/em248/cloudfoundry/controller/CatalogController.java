package com.em248.cloudfoundry.controller;

import java.util.List;

import com.em248.cloudfoundry.entity.Catalog;
import com.em248.cloudfoundry.entity.ServiceDefinition;
import com.em248.cloudfoundry.service.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author tian
 *
 * catalog 信息获取
 */
@Controller
@RequestMapping(value="/v2/catalog")
public class CatalogController {
	
	@Autowired
	private CatalogService service;
	
	private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);


	/**
	 * 添加一个service信息
	 * @param serviceDefinition
	 * @return
	 */
	@ResponseBody
	@PostMapping(consumes="application/json", produces="application/json", value="/services")
	public ServiceDefinition createServiceDefinition(@RequestBody ServiceDefinition serviceDefinition){
		return service.createServiceDefinition(serviceDefinition);
	}


	/**
	 * 根据 service id 删除一个服务
	 * @param serviceInstanceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(produces="application/json", value="/services/{sid}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteServiceDefinition(@PathVariable("sid") String serviceInstanceId){
		boolean deleted = service.deleteServiceDefinition(serviceInstanceId);
		HttpStatus status = deleted ? HttpStatus.OK : HttpStatus.GONE;
		ResponseEntity<String> response = new ResponseEntity<>("{}",status);  
		return response;
	}


	/**
	 * 获取broker的catalog详细信息，信息包括service及对应的plan的具体信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(produces="application/json", method=RequestMethod.GET)
	public Catalog list(){
		List<ServiceDefinition> services = service.listServices();
		Catalog catalog = new Catalog(services);
		return catalog;
	}
}
