package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/1 1:36
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ApiPassengerApplication
{

    public static void main(String[] ags)
    {
        SpringApplication.run(ApiPassengerApplication.class);
    }



}
