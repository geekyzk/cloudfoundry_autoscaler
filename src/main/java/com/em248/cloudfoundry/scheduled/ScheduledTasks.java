package com.em248.cloudfoundry.scheduled;

import com.em248.cloudfoundry.entity.Plan;
import com.em248.cloudfoundry.entity.ServiceInstance;
import com.em248.cloudfoundry.entity.ServiceInstanceBinding;
import com.em248.cloudfoundry.repository.PlanRepository;
import com.em248.cloudfoundry.repository.ServiceInstanceBindingRepository;
import com.em248.cloudfoundry.repository.ServiceInstanceRepository;
import org.cloudfoundry.client.v2.applications.*;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 定时任务，不断扫描已绑定应用的运行信息，并根据需要动态弹性伸缩
 *
 * @author tian
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    ReactorCloudFoundryClient cloudFoundryClient;

    @Autowired
    ServiceInstanceBindingRepository instanceBindingRepository;

    @Autowired
    ServiceInstanceRepository serviceInstanceRepository;
    @Autowired
    PlanRepository planRepository;

    @Scheduled(fixedRate = 1000 * 10)
    public void reportCurrentTime() {
        System.out.println("Scheduling Tasks Examples: The time is now " + dateFormat().format(new Date()));
        List<ServiceInstanceBinding> bindingList = instanceBindingRepository.findAll();
        for (ServiceInstanceBinding s : bindingList) {

            String appGuid = s.getAppGuid();
            SummaryApplicationResponse summary = cloudFoundryClient.applicationsV2().summary(SummaryApplicationRequest.builder().applicationId(appGuid).build()).block();
            if ("STARTED".equals(summary.getState())) {
                ServiceInstance serviceInstance = serviceInstanceRepository.findOne(s.getServiceInstanceId());
                Plan plan = planRepository.findOne(serviceInstance.getPlanId());
                Map<String, String> other = plan.getMetadata().getOther();
                Double addCpu = other.get("addCpu") != null ? Double.parseDouble(other.get("addCpu")) : 0.8;
                Double cutCpu = other.get("cutCpu") != null ? Double.parseDouble(other.get("cutCpu")) : 0.2;
                Integer maxI = other.get("maxI") != null ? Integer.parseInt(other.get("maxI")) : 5;
                Integer minI = other.get("minI") != null ? Integer.parseInt(other.get("minI")) : 1;
                ApplicationStatisticsResponse appStatis = cloudFoundryClient.applicationsV2().statistics(ApplicationStatisticsRequest.builder().applicationId(appGuid).build()).block();
                Set<Map.Entry<String, InstanceStatistics>> entries = appStatis.getInstances().entrySet();
                long overCpuCount = entries.stream()
                        .filter(item -> item.getValue().getState().equals("RUNNING"))
                        .filter(item -> item.getValue().getStatistics().getUsage().getCpu() > addCpu)
                        .count();
                if (overCpuCount >= 1 && entries.size() < maxI) {
                    cloudFoundryClient.applicationsV2().update(UpdateApplicationRequest.builder()
                            .applicationId(appGuid)
                            .instances(entries.size() + 1)
                            .build())
                            .block();
                } else if (overCpuCount < 1 && entries.size() > minI) {
                    long downCpuCount = entries.stream()
                            .filter(item -> item.getValue().getState().equals("RUNNING"))
                            .filter(item -> item.getValue().getStatistics().getUsage().getCpu() < cutCpu)
                            .count();
                    if (downCpuCount >= 1) {
                        cloudFoundryClient.applicationsV2().update(UpdateApplicationRequest.builder()
                                .applicationId(appGuid)
                                .instances(entries.size() - 1)
                                .build())
                                .block();
                    }
                }
            }
        }
    }


    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

}