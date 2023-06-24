package edu.seu.mtyx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients // 应用Feign客户端 -> 发起远程调用
public class ServiceSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSearchApplication.class);
    }
}
