package com.findany.productfeedservice.config;

import com.findany.productfeedservice.entity.ProductFeed;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, ProductFeed> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        JsonDeserializer<ProductFeed> jsonDeserializer = new JsonDeserializer<>(ProductFeed.class);
        jsonDeserializer.addTrustedPackages("com.findany.productservice");
        jsonDeserializer.ignoreTypeHeaders();
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, ProductFeed> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProductFeed> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(new ErrorHandler() {
            @Override
            public void handle(Exception e, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
                if (e.getMessage().contains("Error deserializing key/value for partition ")) {
                    String s = e.getMessage().split("Error deserializing key/value for partition ")[1].split(". If needed, please seek past the record to continue consumption.")[0];
                    String topics = s.substring(0, s.lastIndexOf('-'));
                    int offset = Integer.valueOf(s.split("offset ")[1]);
                    int partition = Integer.valueOf(s.split("-")[3].split(" at")[0]);
                    TopicPartition topicPartition = new TopicPartition(topics, partition);
                    consumer.seek(topicPartition, offset + 1);
                }
            }

            @Override
            public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {

            }

            @Override
            public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord, Consumer<?, ?> consumer) {
                if (e.getMessage().contains("Error deserializing key/value for partition ")) {
                    String s = e.getMessage().split("Error deserializing key/value for partition ")[1].split(". If needed, please seek past the record to continue consumption.")[0];
                    String topics = s.substring(0, s.lastIndexOf('-'));
                    int offset = Integer.valueOf(s.split("offset ")[1]);
                    int partition = Integer.valueOf(s.split("-")[3].split(" at")[0]);
                    TopicPartition topicPartition = new TopicPartition(topics, partition);
                    consumer.seek(topicPartition, offset + 1);
                }
            }
        });
        return factory;
    }

}
