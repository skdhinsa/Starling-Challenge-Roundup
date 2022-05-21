package com.example.serenakd.roundup.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Configuration;

@org.springframework.context.annotation.Configuration
public class RestConfiguration {

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

}