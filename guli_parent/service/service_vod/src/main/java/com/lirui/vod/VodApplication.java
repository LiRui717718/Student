package com.lirui.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/23 20:50
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.lirui"})//包的扫描规则
@EnableDiscoveryClient
public class VodApplication {
    public static void main(String[] args){
        SpringApplication.run(VodApplication.class, args);
    }
}
