package com.appsdeveloperblog.photoapp.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OrdersResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersResourceServerApplication.class, args);
	}

}
