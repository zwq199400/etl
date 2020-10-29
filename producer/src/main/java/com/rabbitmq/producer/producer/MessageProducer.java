package com.rabbitmq.producer.producer;


import com.etl.entity.pojo.BaseEntity;

public interface MessageProducer {
    /**
     * 发送导入的信息
     */
    void sendMessage(BaseEntity baseEntity);
}
