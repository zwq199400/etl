package com.example.discovery.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhouwq
 * @date 2020/8/17 15:40
 */

@RestController
@RefreshScope
public class TestController {

    @Value("${server.port}")
    private Integer port;

    @Value("${docker.name}")
    private String name;

    @PostMapping(value = "/baseList", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() {
        return name;
    }
}
