package com.ccommit.price_tracking_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PriceTrackingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceTrackingServerApplication.class, args);
	}

}
