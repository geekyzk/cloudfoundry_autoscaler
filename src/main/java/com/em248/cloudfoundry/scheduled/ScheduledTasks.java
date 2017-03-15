package com.em248.cloudfoundry.scheduled;

import com.em248.cloudfoundry.entity.ServiceInstanceBinding;
import com.em248.cloudfoundry.repository.ServiceInstanceBindingRepository;
import org.cloudfoundry.client.v2.applications.ApplicationStatisticsRequest;
import org.cloudfoundry.client.v2.applications.ApplicationStatisticsResponse;
import org.cloudfoundry.client.v2.applications.InstanceStatistics;
import org.cloudfoundry.client.v2.applications.UpdateApplicationRequest;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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
public class ScheduledTasks{

    @Autowired
    ReactorCloudFoundryClient cloudFoundryClient;

    @Autowired
    ServiceInstanceBindingRepository instanceBindingRepository;

    @Scheduled(fixedRate = 1000 * 10)
    public void reportCurrentTime(){
        System.out.println ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date()));

        Iterable<ServiceInstanceBinding> instanceBindings = instanceBindingRepository.findAll();
        Iterator<ServiceInstanceBinding> serviceInstanceBindingIterator = instanceBindings.iterator();
        while(serviceInstanceBindingIterator.hasNext()){
            ServiceInstanceBinding serviceInstanceBinding = serviceInstanceBindingIterator.next();
            String appGuid = serviceInstanceBinding.getAppGuid();
            ApplicationStatisticsResponse appStatis = cloudFoundryClient.applicationsV2().statistics(ApplicationStatisticsRequest.builder().applicationId(appGuid).build()).block();
            Set<Map.Entry<String, InstanceStatistics>> entries = appStatis.getInstances().entrySet();
            for (Map.Entry<String,InstanceStatistics> item : entries){
                System.out.println(item.getKey());
                System.out.println(item.getValue().getState());
                Double cpu = item.getValue().getStatistics().getUsage().getCpu();
                if(cpu > 0.8 ){
                    if (entries.size() < 5 ) {
                        cloudFoundryClient.applicationsV2()
                                .update(UpdateApplicationRequest.builder()
                                        .applicationId(appGuid)
                                        .instances(entries.size() + 1)
                                        .build())
                                .block();
                    }
                }
            }
        }
    }


    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss");
    }

}