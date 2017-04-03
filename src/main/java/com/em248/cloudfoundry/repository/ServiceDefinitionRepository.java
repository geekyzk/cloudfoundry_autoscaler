package com.em248.cloudfoundry.repository;

import com.em248.cloudfoundry.entity.ServiceDefinition;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceDefinitionRepository extends JpaRepository<ServiceDefinition, String> {

}
