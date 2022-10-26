package com.atguigu.ggkt.vod;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/22 15:43
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.atguigu.ggkt.vod.mapper")
@ComponentScan("com.atguigu")
public class SpringVodApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringVodApplication.class);
    }
}
