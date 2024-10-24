package com.lameute.ride_service.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaRideTopicConfig {

  @Bean
  public NewTopic paymentTopic() {
    return TopicBuilder
            .name("notification-topic")
            .partitions(10)
            .replicas(1)
            .build();
  }
}
