package com.rabbitmq.consumer.consumer;

import com.etl.entity.pojo.BaseEntity;
import com.rabbitmq.consumer.binders.TestInputBinder;
import com.rabbitmq.consumer.constant.TestBinderConstant;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author zhouwq
 * @date 2020/10/30 15:12
 */
@EnableBinding(TestInputBinder.class)
public class TestConsumer {


    @Resource
    private RestHighLevelClient restHighLevelClient;

    @StreamListener(value = TestBinderConstant.TEST_QUEUE_INPUT)
    public void testConsumer(BaseEntity message) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        IndexRequest indexRequest = new IndexRequest();
        indexRequest
                .index("idx_user")
                .setRefreshPolicy(WriteRequest.RefreshPolicy.NONE)
                .source(message);
        bulkRequest.add(indexRequest);
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(message.toString());
    }
}
