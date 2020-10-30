package com.rabbitmq.consumer.binders;

import com.rabbitmq.consumer.constant.TestBinderConstant;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * @author zhouwq
 * @date 2020/10/29 9:52
 */

@Service("testInputBinder")
public interface TestInputBinder {

    @Input(TestBinderConstant.TEST_QUEUE_INPUT)
    MessageChannel testQueueInput();
}
