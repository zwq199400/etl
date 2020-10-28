package com.rabbitmq.consumer;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsumerApplicationTests {

    @Test
    void contextLoads() {
    }
    public static void main(String[] args) {
        CreateIndexRequest request = new CreateIndexRequest("test_idx123");
        JSONObject outJsonObject = new JSONObject();
        outJsonObject.put("addTime", (JSONObject) JSONObject.parse("{\"type\":\"date\",\"format\":\"epoch_second\"}"));
        String jj = "{\n" +
                "  \"properties\": {\n" + outJsonObject.toJSONString() +
                "  }\n" +
                "}";
        System.out.println();
    }
}
