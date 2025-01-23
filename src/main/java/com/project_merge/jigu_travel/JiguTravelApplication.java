package com.project_merge.jigu_travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients  // FeignClient 활성화
public class JiguTravelApplication {

	public static void main(String[] args) {

		SpringApplication.run(JiguTravelApplication.class, args);

	}

}
