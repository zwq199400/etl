#### ETL功能实现

###### 一、概述
   
    目的：实现mysql到es库的ETL功能
    
    服务注册发现中间件为nacos。
    项目集成canal获取mysql binlog数据，导入至es库中，rabbitMQ作为消息中间件作为缓冲层处理

###### 二、流程说明

    环境搭建过程略过，主要中间件有rabbitMQ、canal、elasticSearch（version:7.9）、logstash、Kibana、nacos等，都为单机部署
    
    1. 生产者获取canal中的binlog数据
    2. 将binlog数据作为message发送到指定rabbitMQ队列中
    3. 消费者监听队列，获取message
    4. 将获取到的message导入到es库中
    
    注：方案可直接换logstash作为消费者接入rabbitMQ，直接导入数据入es中，logstash配置文件可参考es官网说明
    官网地址：https://www.elastic.co/guide/en/logstash/7.9/introduction.html