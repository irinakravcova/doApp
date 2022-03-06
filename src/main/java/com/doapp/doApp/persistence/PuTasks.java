package com.doapp.doApp.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.doapp"
)
// Persistence Unit (pu) initialization. Stolen from internet.
public class PuTasks {

}
