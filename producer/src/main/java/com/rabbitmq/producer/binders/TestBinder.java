package com.rabbitmq.producer.binders;

import com.rabbitmq.producer.constant.TestBinderConstant;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * @author zhouwq
 * @date 2020/10/29 9:52
 */

@Service("testBinder")
public interface TestBinder {

    @Output(TestBinderConstant.TEST_QUEUE)
    MessageChannel testQueue();
}
