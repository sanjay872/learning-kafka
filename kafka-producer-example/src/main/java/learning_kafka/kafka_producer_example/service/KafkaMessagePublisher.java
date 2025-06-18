package learning_kafka.kafka_producer_example.service;

import learning_kafka.kafka_producer_example.entity.Customer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {
    private final KafkaTemplate<String,Object> template;

    public KafkaMessagePublisher(
           KafkaTemplate<String,Object> template
    ){
        this.template=template;
    }

    public void publishMessage(String message){
        CompletableFuture<SendResult<String,Object>> future=template.send("learn-kafka",3,null,message);
        future.whenComplete((result,ex)->{
            if(ex==null){
                System.out.println("Send Message=["+message+"] with offset=["+result.getRecordMetadata().offset()+"]");
            }
            else{
                System.out.println("Unable to get the message=["+message+"] due to : "+ex.getMessage());
            }
        });
    }

    public void publishMessage(Customer customer){
        CompletableFuture<SendResult<String,Object>> future=template.send("learning-kafka-cust",customer);
        future.whenComplete((result,ex)->{
            if(ex==null){
                System.out.println("Send Message=["+customer.toString()+"] with offset=["+result.getRecordMetadata().offset()+"]");
            }
            else{
                System.out.println("Unable to get the message=["+customer.toString()+"] due to : "+ex.getMessage());
            }
        });
    }
}
