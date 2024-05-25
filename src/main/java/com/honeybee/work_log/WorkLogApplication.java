package com.honeybee.work_log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
public class WorkLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkLogApplication.class, args);
    }

}
