package com.example.smarttaskmanager.config;

import com.example.smarttaskmanager.kafka.TaskCreatedEvent;
import com.example.smarttaskmanager.kafka.TaskReminderEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, TaskCreatedEvent> taskCreatedConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "task-consumer-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.smarttaskmanager.kafka");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.example.smarttaskmanager.kafka.TaskCreatedEvent");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TaskCreatedEvent> taskCreatedKafkaListenerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, TaskCreatedEvent>();
        factory.setConsumerFactory(taskCreatedConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, TaskReminderEvent> taskReminderConsumerFactory() {
        JsonDeserializer<TaskReminderEvent> deserializer = new JsonDeserializer<>(TaskReminderEvent.class);
        deserializer.addTrustedPackages("com.example.smarttaskmanager.kafka");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "task-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TaskReminderEvent> taskReminderKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TaskReminderEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(taskReminderConsumerFactory());
        return factory;
    }
}