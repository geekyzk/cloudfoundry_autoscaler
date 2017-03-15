package com.em248.cloudfoundry.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.em248.cloudfoundry.entity.*;
import com.em248.cloudfoundry.repository.PlanRepository;
import com.em248.cloudfoundry.repository.ServiceDefinitionRepository;
import com.em248.cloudfoundry.repository.ServiceInstanceBindingRepository;
import com.em248.cloudfoundry.repository.ServiceInstanceRepository;
import com.em248.cloudfoundry.service.BaseService;
import com.em248.cloudfoundry.service.ServiceManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServiceManagementImpl extends BaseService implements ServiceManagement {
    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ServiceDefinitionRepository serviceRepository;

    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    @Autowired
    private ServiceInstanceBindingRepository bindingRepository;


    /**
     * 创建服务实例 service
     *
     * @param serviceRequest
     * @return
     */
    @Override
    public ServiceInstance createInstance(CreateServiceInstanceRequest serviceRequest) {
        ServiceDefinition serviceDefinition = serviceRepository.findOne(serviceRequest.getServiceDefinitionId());
        if (serviceDefinition == null) { // 判断数据库中是否有对应的service
            throw new IllegalArgumentException("Service definition not found: " + serviceRequest.getServiceDefinitionId());
        }
        Plan plan = planRepository.findOne(serviceRequest.getPlanId());
        if (plan == null) { // 判断数据库中是否有对应的plan
            throw new IllegalArgumentException("Invalid plan identifier");
        }
        if (serviceInstanceRepository.exists(serviceRequest.getServiceInstanceId())) { //查重，判断实例是否存在
            throw new IllegalStateException("There's already an instance of this service");
        }
        //check instance count
        Long count = serviceInstanceRepository.countByPlanId(serviceRequest.getPlanId()); // 获取现有对应plan的实例数量
        //get max instance count
        String maxInstances = "100"; // 设置最大实例数
        if (count >= Long.parseLong(maxInstances)) {
            throw new IllegalStateException("Can not create new instance, the instance has reached the upper limit.");
        }
        //把服务实例相关信息存到数据中
        ServiceInstance instance = new ServiceInstance(serviceRequest.getServiceInstanceId(), serviceDefinition.getId(), plan.getId(), serviceRequest.getOrganizationGuid(), serviceRequest.getSpaceGuid(), "");
        instance = serviceInstanceRepository.save(instance);
        return instance;
    }


    /**
     * 删除服务实例
     *
     * @param serviceInstanceId
     * @return
     */
    @Override
    public boolean removeServiceInstance(String serviceInstanceId) {
        if (!serviceInstanceRepository.exists(serviceInstanceId)) { // 判断是否存在
            return false;
        }
        if (bindingRepository.countByServiceInstanceId(serviceInstanceId) > 0) { // 判断服务实例是否绑定有应用
            throw new IllegalStateException("Can not delete service instance, there are still apps bound to it");
        }
        ServiceInstance instance = serviceInstanceRepository.findOne(serviceInstanceId);
        serviceInstanceRepository.delete(serviceInstanceId);
        return true;
    }

    @Override
    public List<ServiceInstance> listInstances() {
        return makeCollection(serviceInstanceRepository.findAll());
    }


    /**
     * 绑定服务实例到应用上
     *
     * @param bindingRequest
     * @return
     */
    @Override
    public ServiceInstanceBinding createInstanceBinding(ServiceInstanceBindingRequest bindingRequest) {
        if (bindingRepository.exists(bindingRequest.getBindingId())) { // 判断是否已绑定
            throw new IllegalStateException("Binding Already exists");
        }
        ServiceInstance instance = serviceInstanceRepository.findOne(bindingRequest.getInstanceId());
        Plan plan = planRepository.findOne(bindingRequest.getPlanId());
        //get credential
//		MysqlSetting mysqlSetting = appConfig.getMysqlSettingByKey(plan.getMetadata().getOther().get("type"));
        Map<String, String> credentials = new HashMap<>();
        credentials.put("info", "autoscaler");
        ServiceInstanceBinding binding = new ServiceInstanceBinding();
        binding.setId(bindingRequest.getBindingId());
        binding.setServiceInstanceId(bindingRequest.getInstanceId());
        binding.setAppGuid(bindingRequest.getAppGuid());
        binding.setCredentials(credentials);
        binding = bindingRepository.save(binding);
        return binding;
    }


    /**
     * 解绑
     *
     * @param serviceBindingId
     * @return
     */
    @Override
    public boolean removeBinding(String serviceBindingId) {
        ServiceInstanceBinding binding = bindingRepository.findOne(serviceBindingId);
        if (binding == null) {
            return false;
        }
        ServiceInstance instance = serviceInstanceRepository.findOne(binding.getServiceInstanceId());
        bindingRepository.delete(binding);
        return true;
    }

    @Override
    public List<ServiceInstanceBinding> listBindings() {
        // TODO Auto-generated method stub
        return null;
    }
}
