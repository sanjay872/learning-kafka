package learning_kafka.kafka_producer_example.controller;

import learning_kafka.kafka_producer_example.entity.Customer;
import learning_kafka.kafka_producer_example.service.KafkaMessagePublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produce-msg")
public class EventController {

    private final KafkaMessagePublisher publisher;

    public EventController(
            KafkaMessagePublisher publisher
    ){
        this.publisher=publisher;
    }

    @PostMapping
    public ResponseEntity<?> publishMessage(@RequestBody String message){
        try {
            publisher.publishMessage(message);
            return ResponseEntity.ok("Message Published!");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cust")
    public ResponseEntity<?> publishCustomerInfo(@RequestBody Customer customer){
        try{
            publisher.publishMessage(customer);
            return ResponseEntity.ok("Customer Published!");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
