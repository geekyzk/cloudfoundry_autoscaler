package com.em248.cloudfoundry.service.impl;

import java.io.InputStream;

import com.em248.cloudfoundry.entity.ServiceDefinition;
import com.em248.cloudfoundry.repository.ServiceDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author tian
 */
@Component
public class ContextStartedListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ServiceDefinitionRepository repository;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			InputStream in = ContextStartedListener.class.getClassLoader().getResourceAsStream("ServiceDescription.json");
			ServiceDefinition def = mapper.readValue(in, ServiceDefinition.class);
			if(repository.count() == 0){
				repository.save(def);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
