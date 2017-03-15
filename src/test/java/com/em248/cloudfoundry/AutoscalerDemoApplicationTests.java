package com.em248.cloudfoundry;

import org.cloudfoundry.client.v2.applications.*;
import org.cloudfoundry.client.v2.servicebrokers.UpdateServiceBrokerRequest;
import org.cloudfoundry.client.v2.serviceplans.UpdateServicePlanRequest;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoscalerDemoApplicationTests {

    @Autowired
    ReactorCloudFoundryClient cloudFoundryClient;


    @Test
    public void contextLoads() {
//		String appGuid = "18379930-b031-4b5f-96f7-1c85491920ca";
//		ApplicationStatisticsResponse appStatis = cloudFoundryClient.applicationsV2().statistics(ApplicationStatisticsRequest.builder().applicationId(appGuid).build()).block();
//		Set<Map.Entry<String, InstanceStatistics>> entries = appStatis.getInstances().entrySet();
//		for (Map.Entry<String,InstanceStatistics> item : entries){
//			System.out.println(item.getKey());
//			System.out.println(item.getValue().getState());
//			Double cpu = item.getValue().getStatistics().getUsage().getCpu();
//			if(cpu > 0.8 ){
//				if (entries.size() < 5 ) {
//					cloudFoundryClient.applicationsV2()
//							.update(UpdateApplicationRequest.builder()
//									.applicationId(appGuid)
//									.instances(entries.size() + 1)
//								.build())
//							.block();
//				}
//			}
//		}
//	}
    }

    @Test
    public void setPublic() {
        cloudFoundryClient.servicePlans().update(UpdateServicePlanRequest.builder()
                .servicePlanId("6fa4b6ee-7359-4b2a-9365-39d7ecb9fb80")
                .publiclyVisible(true)
                .build())
                .block();

    }

}
