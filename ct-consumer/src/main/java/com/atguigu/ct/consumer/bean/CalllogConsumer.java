package com.atguigu.ct.consumer.bean;

import com.atguigu.ct.common.bean.Consumer;
import com.atguigu.ct.common.constant.Names;

import com.atguigu.ct.consumer.dao.HBaseDao;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class CalllogConsumer implements Consumer {
    @Override
    public void consume() {

        try {
            Properties prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("consumer.properties"));
            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);
            consumer.subscribe(Arrays.asList(Names.TOPIC.getValue()));

            HBaseDao dao = new HBaseDao();
            dao.init();

            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    System.out.println(consumerRecord.value());
                    dao.insertData(consumerRecord.value());
/*                    Calllog log = new Calllog(consumerRecord.value());
                    dao.insertData(log);*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void close() throws IOException {

    }
}
