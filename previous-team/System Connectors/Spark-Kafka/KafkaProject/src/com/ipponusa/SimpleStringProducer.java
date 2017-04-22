package com.ipponusa;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class SimpleStringProducer 
{
	public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9097");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 1000; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("abhi", "value-" + i);
            producer.send(record);
            
        }

        producer.close();
    }

}
