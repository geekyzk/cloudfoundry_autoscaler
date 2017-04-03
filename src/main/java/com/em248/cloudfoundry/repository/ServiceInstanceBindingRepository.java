package com.em248.cloudfoundry.repository;

import com.em248.cloudfoundry.entity.ServiceInstanceBinding;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceInstanceBindingRepository extends JpaRepository<ServiceInstanceBinding, String> {
	public Long countByServiceInstanceId(String serviceInstanceId);
}
