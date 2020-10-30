package com.rabbitmq.consumer.consumer;

import com.etl.entity.pojo.BaseEntity;
import com.rabbitmq.consumer.binders.TestInputBinder;
import com.rabbitmq.consumer.constant.TestBinderConstant;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @author zhouwq
 * @date 2020/10/30 15:12
 */
@EnableBinding(TestInputBinder.class)
public class TestConsumer {

    @StreamListener(value = TestBinderConstant.TEST_QUEUE_INPUT)
    public void testConsumer(BaseEntity message) {
        System.out.println(message.toString());
    }
}
