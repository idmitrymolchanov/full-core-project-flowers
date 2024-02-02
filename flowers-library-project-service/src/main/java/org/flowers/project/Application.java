package org.flowers.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
@EnableFeignClients(basePackages = "org.flowers.project")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}