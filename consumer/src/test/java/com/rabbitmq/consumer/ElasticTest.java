package com.rabbitmq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouwq
 * @date 2020/10/28 9:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticTest {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void add() {
        try {
            CreateIndexRequest request = new CreateIndexRequest("idx_user");
            request.mapping(
                    "{\n" +
                            "  \"properties\": {\n" +
                            "\"id\" : {\n" +
                            "          \"type\" : \"long\"\n" +
                            "        }," +
                            "\"name\": {\n" +
                            "            \"type\": \"text\",\n" +
                            "            \"fields\": {\n" +
                            "              \"keyword\": {\n" +
                            "                \"type\": \"keyword\",\n" +
                            "                \"ignore_above\": 256\n" +
                            "              }\n" +
                            "            }\n" +
                            "          }," +
                            "\"age\" : {\n" +
                            "          \"type\" : \"long\"\n" +
                            "        }" +
                            "  }\n" +
                            "}",
                    XContentType.JSON);

            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            System.out.println(acknowledged);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        try {
            DeleteIndexRequest deleteRequest = new DeleteIndexRequest("test_idx123");

            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteRequest, RequestOptions.DEFAULT);
            boolean acknowledged = acknowledgedResponse.isAcknowledged();
            System.out.println(acknowledged);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
