package com.em248.cloudfoundry.repository;

import com.em248.cloudfoundry.entity.ServiceInstanceBinding;
import org.springframework.data.repository.CrudRepository;


public interface ServiceInstanceBindingRepository extends CrudRepository<ServiceInstanceBinding, String> {
	public Long countByServiceInstanceId(String serviceInstanceId);
}
