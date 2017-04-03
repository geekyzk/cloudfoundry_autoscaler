package com.em248.cloudfoundry.repository;

import com.em248.cloudfoundry.entity.Plan;
import com.em248.cloudfoundry.entity.ServiceDefinition;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanRepository extends JpaRepository<Plan, String> {
    public Long countByServiceDefinition(ServiceDefinition definition);

    public Plan findByServiceDefinition(ServiceDefinition definition);
}
