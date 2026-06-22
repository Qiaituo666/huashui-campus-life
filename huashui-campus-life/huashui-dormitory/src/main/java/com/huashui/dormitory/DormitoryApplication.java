package com.huashui.dormitory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.huashui.client")
public class DormitoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DormitoryApplication.class, args);
    }
}
