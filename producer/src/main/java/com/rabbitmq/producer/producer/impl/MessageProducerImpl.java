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
 * @author 梁鲁江
 * @version v1.0
 * @date 2019/9/18 16:29
 * @since v1.0
 */
@Service("importProducer")
@EnableBinding({TestBinder.class})
public class MessageProducerImpl implements MessageProducer {
    private static final Logger LOG = LoggerFactory.getLogger(MessageProducerImpl.class);
    @Resource
    private TestBinder testBinder;
    @Override
    public void sendMessage(BaseEntity baseEntity) {
        MessageBuilder<BaseEntity> messageBuilder = MessageBuilder.withPayload(baseEntity);;
        Message<BaseEntity> message = messageBuilder.build();
        boolean send = testBinder.testBinder().send(message);
        if(!send){
            LOG.error("导入消息发送失败");
        }
    }
}
