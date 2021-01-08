package com.rabbitmq.producer.producer.impl;

import com.etl.entity.pojo.BaseEntity;
import com.rabbitmq.producer.binders.TestBinder;
import com.rabbitmq.producer.producer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhouwq
 * @date 2021/1/8 13:58
 */
@Service("messageProducer")
@EnableBinding({TestBinder.class})
public class MessageProducerImpl implements MessageProducer {
    private static final Logger LOG = LoggerFactory.getLogger(MessageProducerImpl.class);
    @Resource
    private TestBinder testBinder;
    @Override
    public void sendMessage(BaseEntity baseEntity) {
        MessageBuilder<BaseEntity> messageBuilder = MessageBuilder.withPayload(baseEntity);;
        Message<BaseEntity> message = messageBuilder.build();
        boolean send = testBinder.testQueue().send(message);
        if(!send){
            LOG.error("消息发送失败");
        }
    }
}
