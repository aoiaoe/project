package com.cz.springcloudconsumerribbon92xx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/13 上午 9:54
 * @since JDK8
 */
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class Test1 {


    @Autowired
    private DiscoveryClient discoveryClient;

    @Test
    public void test() {
        for (String service : discoveryClient.getServices()) {
            System.out.println(service);
        }
    }

    @Test
    public void test1() {
        final List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        for (ServiceInstance si : provider) {
            System.out.println(si.getScheme() + "://" + si.getHost() + ":" + si.getPort());
        }
    }
}
