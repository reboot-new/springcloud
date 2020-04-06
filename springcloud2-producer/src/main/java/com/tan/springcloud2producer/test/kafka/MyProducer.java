package com.tan.springcloud2producer.test.kafka;

import com.alibaba.fastjson.JSON;
import com.tan.springcloud2producer.entity.Student;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;



public class MyProducer {
    private static KafkaProducer<String, String> producer;

    static {
        Properties kfkProperties = new Properties();
        kfkProperties.put("bootstrap.servers", "192.168.233.128:9092");
        kfkProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kfkProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(kfkProperties);
    }

    /**
     * producer1 发送消息不考虑返回信息
     */
    private static void sendMessageForgotResult() {

        Student s =new Student("TOM",54);
        String str =  JSON.toJSONString(s);


        ProducerRecord<String, String> record = new ProducerRecord<>("test", "name", str);
        producer.send(record);
        producer.close();
    }

    /**
     * producer2 发送消息同步等到发送成功
     */

    private static void sendMessageSync() throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord<>("test", "name", "Sync");
        RecordMetadata result = producer.send(record).get();

        System.out.println("时间戳，主题，分区，位移: " + result.timestamp() + "," + record.topic() + "," + result.partition() + "," + result.offset());

        producer.close();
    }

    /**
     * producer3 发送消息异步回调返回消息
     */
    private static void sendMessageCallBack() {
        ProducerRecord<String, String> record;
        while (true) {
            record = new ProducerRecord<>("test", "name", "CallBack");
            producer.send(record, new MyProducerCallBack());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        producer.close();
    }

    private static class MyProducerCallBack implements Callback {

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (null != e) {
                e.printStackTrace();
                return;
            }
            System.out.println("时间戳，主题，分区，位移: " + recordMetadata.timestamp() + ", " + recordMetadata.topic() + "," + recordMetadata.partition() + " " + recordMetadata.offset());
        }
    }

    public static void main(String args[]) throws Exception {
        MyProducer.sendMessageForgotResult();
//        MyProducer.sendMessageSync();
//        MyProducer.sendMessageCallBack();
    }
}
