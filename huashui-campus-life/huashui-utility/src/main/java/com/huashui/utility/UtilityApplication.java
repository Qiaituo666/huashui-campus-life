package com.huashui.utility;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.huashui.client")
@EnableScheduling
public class UtilityApplication {
    public static void main(String[] args) {
        SpringApplication.run(UtilityApplication.class, args);
    }
}
