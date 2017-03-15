package com.em248.cloudfoundry.controller;

import java.util.List;

import com.em248.cloudfoundry.entity.*;
import com.em248.cloudfoundry.service.ServiceManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author tian
 *         servicee instance 相关
 */
@Controller
@RequestMapping("/v2/service_instances")
public class ServiceController {

    @Autowired
    private ServiceManagement service;

    @ResponseBody
    @PutMapping(consumes = "application/json", produces = "application/json", value = "/{instanceId}")
    public ResponseEntity<CreateServiceInstanceResponse> provision(@RequestBody CreateServiceInstanceRequest serviceRequest, @PathVariable("instanceId") String serviceInstanceId) {
        serviceRequest.setServiceInstanceId(serviceInstanceId);
        ServiceInstance instance = service.createInstance(serviceRequest);
        ResponseEntity<CreateServiceInstanceResponse> response = new ResponseEntity<>(new CreateServiceInstanceResponse(instance), HttpStatus.CREATED);
        return response;
    }

    @DeleteMapping(produces = "application/json", value = "/{instanceId}")
    public ResponseEntity<String> deprovision(@PathVariable("instanceId") String serviceInstanceId) {
        boolean deleted = service.removeServiceInstance(serviceInstanceId);
        HttpStatus status = deleted ? HttpStatus.OK : HttpStatus.GONE;
        ResponseEntity<String> response = new ResponseEntity<>("{}", status);
        return response;
    }

    @ResponseBody
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ServiceInstance>> listInstances() {
        ResponseEntity<List<ServiceInstance>> response = new ResponseEntity<>(service.listInstances(), HttpStatus.OK);
        return response;
    }

    @ResponseBody
    @PutMapping(value = "/{instanceId}/service_bindings/{bindingId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ServiceInstanceBindingResponse> bind(@RequestBody ServiceInstanceBindingRequest request, @PathVariable("instanceId") String serviceInstanceId, @PathVariable("bindingId") String bindingId) {
        request.setBindingId(bindingId);
        request.setInstanceId(serviceInstanceId);
        ServiceInstanceBinding binding = service.createInstanceBinding(request);
        return new ResponseEntity<ServiceInstanceBindingResponse>(
                new ServiceInstanceBindingResponse(binding),
                HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{instanceId}/service_bindings/{bindingId}", produces = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity<String> unbind(@PathVariable("instanceId") String serviceInstanceId, @PathVariable("bindingId") String bindingId) {
        boolean deleted = service.removeBinding(bindingId);
        HttpStatus status = deleted ? HttpStatus.OK : HttpStatus.GONE;
        ResponseEntity<String> response = new ResponseEntity<>("{}", status);
        return response;
    }


}
