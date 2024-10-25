package com.lameute.ride_service.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaRideTopicConfig {

  /* Configuration of a rideStart kafka topic for message sending*/
  @Bean
  public NewTopic rideStartTopic() {
    return TopicBuilder
            .name("rideStart-topic")
            .partitions(10)
            .replicas(1)
            .build();
  }

  /* Configuration of a rideStop kafka topic for message sending*/
  @Bean
  public NewTopic rideStopTopic() {
    return TopicBuilder
            .name("rideStop-topic")
            .partitions(10)
            .replicas(1)
            .build();
  }
}
