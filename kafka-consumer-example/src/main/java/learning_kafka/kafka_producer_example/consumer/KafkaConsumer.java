package learning_kafka.kafka_producer_example.consumer;

import learning_kafka.kafka_producer_example.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    Logger logger= LoggerFactory.getLogger(KafkaConsumer.class);

    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 15000)) // 3 attempts will be done
    @KafkaListener(topics = "learn-kafka",groupId = "learn-kafka-grp-1",
            topicPartitions = @TopicPartition(topic = "learn-kafka-grp-1",partitions = {"3"}))
    public void consumerMessage1(String message){
        logger.info("consumer 1 consumed message: {}",message);
    }

    @RetryableTopic(attempts = "4", exclude = {NullPointerException.class})
    @KafkaListener(topics = "learn-kafka",groupId = "learn-kafka-grp-1")
    public void consumerMessage2(String message){
        logger.info("consumer 2 consumed message: {}",message);
    }

    @KafkaListener(topics = "learn-kafka",groupId = "learn-kafka-grp-1")
    public void consumerMessage3(String message){
        logger.info("consumer 3 consumed message: {}",message);
    }

    @KafkaListener(topics = "learning-kafka-cust",groupId = "learning-kafka-cust-1")
    public void consumerCustomer(Customer customer){
        logger.info("Customer data:{}",customer.toString());
    }


    @DltHandler
    public void listenDLT(Customer customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset){
        logger.info("DLT received: {} , from {} , offset {}",customer.getId(),topic,offset);
    }

}
