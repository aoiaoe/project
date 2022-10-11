package com.cz.spring_cloud_alibaba_gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.WebFluxSleuthOperators;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudAlibabaGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudAlibabaGatewayApplication.class, args);
	}


	@Autowired
	Tracer tracer;

	@Autowired
	CurrentTraceContext currentTraceContext;

	@GetMapping
	public void logTest(ServerWebExchange exchange) {
		WebFluxSleuthOperators.withSpanInScope(tracer, currentTraceContext, exchange, () -> log.error("TESTABCTEST"));
	}
}
