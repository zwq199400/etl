package com.rabbitmq.producer.producer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.etl.entity.pojo.BaseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouwq
 * @date 2020/10/16 11:17
 */

@Component
public class CanalMessageHelp {

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5,
            60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), r -> {
        String name = "test_thread";
        return new Thread(r, name);
    });

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
//                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
//                    printColumn(rowData.getAfterColumnsList());
                } else {
//                    System.out.println("-------&gt; before");
//                    printColumn(rowData.getBeforeColumnsList());
//                    System.out.println("-------&gt; after");
//                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void send(List<Column> columns, Integer eventType) {
        for (Column column : columns) {
            BaseEntity baseEntity = new BaseEntity();
            JSONObject param = new JSONObject();
            param.put(column.getName(), column.getValue());
            baseEntity.setEventType(eventType);
            baseEntity.setIndex("idx_user");
            baseEntity.setParam(param);
            //发送该实体类
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    @PostConstruct
    public void run() {
        Runnable runnable = () -> {
            // 创建链接
            CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("122.51.126.131",
                    11111), "example", "", "");
            int batchSize = 1000;
            int emptyCount = 0;
            try {
                connector.connect();
                connector.subscribe(".*\\..*");
                connector.rollback();
                int totalEmptyCount = 120;
                while (emptyCount < totalEmptyCount) {
                    // 获取指定数量的数据
                    Message message = connector.getWithoutAck(batchSize);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        emptyCount++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignored) {
                        }
                    } else {
                        emptyCount = 0;
                        printEntry(message.getEntries());
                    }
                    // 提交确认
                    connector.ack(batchId);
                }

                System.out.println("empty too many times, exit");
            } finally {
                connector.disconnect();
            }
        };
        threadPool.execute(runnable);
    }
}
