package com.example.smarttaskmanager.config;

import com.example.smarttaskmanager.kafka.TaskCreatedEvent;
import com.example.smarttaskmanager.kafka.TaskReminderEvent;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, TaskCreatedEvent> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    private Map<String, Object> baseConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }

    @Bean
    public ProducerFactory<String, TaskCreatedEvent> taskCreatedProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseConfigs());
    }

    @Bean
    public KafkaTemplate<String, TaskCreatedEvent> taskCreatedKafkaTemplate() {
        return new KafkaTemplate<>(taskCreatedProducerFactory());
    }

    @Bean
    public ProducerFactory<String, TaskReminderEvent> taskReminderProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseConfigs());
    }

    @Bean
    public KafkaTemplate<String, TaskReminderEvent> taskReminderKafkaTemplate() {
        return new KafkaTemplate<>(taskReminderProducerFactory());
    }
}