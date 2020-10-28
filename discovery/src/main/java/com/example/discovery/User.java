package com.example.discovery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouwq
 * @date 2020/8/31 15:22
 */

@Configuration
public class User {

    private String userName;

    private String id;

    @Bean
    public User getUser() {
        return new User();
    }
}
