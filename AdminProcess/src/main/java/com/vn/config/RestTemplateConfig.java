package com.vn.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableScheduling
public class RestTemplateConfig {

	@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
       return builder.build();
    }
	
	
	@Scheduled(fixedDelay = 5000)
	public void scheduleFixedDelayTask() {
	 
	}

}
