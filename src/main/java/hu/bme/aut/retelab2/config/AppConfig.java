package hu.bme.aut.retelab2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import scheduling.ExpirationScheduler;

@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    ExpirationScheduler scheduler() {
        return new ExpirationScheduler();
    }
}
