package com.mashibing.apiBoss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
public class ApiBossApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiBossApplication.class);
    }
}
