//package com.em248.cloudfoundry;
//
//import org.cloudfoundry.client.v2.serviceplans.UpdateServicePlanRequest;
//import org.cloudfoundry.client.v2.serviceplans.UpdateServicePlanResponse;
//import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
//import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.stream.Stream;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class autoscalerDemoApplicationTests {
//
//    @Autowired
//    ReactorCloudFoundryClient cloudFoundryClient;
//    @Autowired
//    DefaultCloudFoundryOperations cloudFoundryOperations;
//
//
//    @Test
//    public void contextLoads() {
////		String appGuid = "18379930-b031-4b5f-96f7-1c85491920ca";
////		ApplicationStatisticsResponse appStatis = cloudFoundryClient.applicationsV2().statistics(ApplicationStatisticsRequest.builder().applicationId(appGuid).build()).block();
////		Set<Map.Entry<String, InstanceStatistics>> entries = appStatis.getInstances().entrySet();
////		for (Map.Entry<String,InstanceStatistics> item : entries){
////			System.out.println(item.getKey());
////			System.out.println(item.getValue().getState());
////			Double cpu = item.getValue().getStatistics().getUsage().getCpu();
////			if(cpu > 0.8 ){
////				if (entries.size() < 5 ) {
////					cloudFoundryClient.applicationsV2()
////							.update(UpdateApplicationRequest.builder()
////									.applicationId(appGuid)
////									.instances(entries.size() + 1)
////								.build())
////							.block();
////				}
////			}
////		}
////	}
//    }
//
//    @Test
//    public void setPublic() {
//        UpdateServicePlanResponse block = cloudFoundryClient.servicePlans().update(UpdateServicePlanRequest.builder()
//                .servicePlanId("24b63f2b-17b6-473e-9a53-96ace18163a5")
//                .publiclyVisible(true)
//                .build())
//                .block();
//
////        cloudFoundryOperations.services().listInstances().subscribe(serviceInstanceSummary ->{
////            System.out.println(serviceInstanceSummary.toString());
////        } );
////        }
//
//        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
//    }
//}
