package learning_kafka.kafka_producer_example.entity;

import lombok.Data;

@Data
public class Customer {
    private long id;
    private String name;
    private String email;
    private String contactNo;
}
