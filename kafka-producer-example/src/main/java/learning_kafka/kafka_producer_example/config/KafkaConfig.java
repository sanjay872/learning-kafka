package learning_kafka.kafka_producer_example.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic createTopic(){
        return new NewTopic("learning-kafka-2",5,  (short) 1);
    }

    @Bean
    public NewTopic createCustomerTopic(){
        return new NewTopic("learning-kafka-cust",5,  (short) 1);
    }
}
