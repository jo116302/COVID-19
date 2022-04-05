package com.COVID19.config;

import com.COVID19.repository.EventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    /*
     * Bean 등록을 위해서 존재했지만, JPA를 사용하면서 EventRepository가 자동으로 등록되어 필요없어진다.
     */
    /*
    @Bean
    public EventRepository eventRepository() {
        return new EventRepository() {};
    }
    */
}
