package com.rabbitmq.consumer;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author zhouwq
 * @date 2020/10/27 16:45
 */
@RestController
public class TestController {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @PostMapping(value = "/getActivityWeb", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testA() {
        return "sada";
    }
}
