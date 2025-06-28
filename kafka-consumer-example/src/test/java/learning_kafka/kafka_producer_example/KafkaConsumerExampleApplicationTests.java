package learning_kafka.kafka_producer_example;

import learning_kafka.kafka_producer_example.entity.Customer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class KafkaConsumerExampleApplicationTests {

	Logger logger= LoggerFactory.getLogger(KafkaConsumerExampleApplicationTests.class);

	@Container
	static KafkaContainer kafka=new KafkaContainer(DockerImageName.parse("apache/kafka:latest"));

	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry){
		registry.add("spring.kafka.bootstrap-servers",kafka::getBootstrapServers);
	}

	@Autowired
	private KafkaTemplate<String,Object> kafkaTemplate;

	@Test
	public void testConsumeEvent(){
		logger.info("test consume event execution started.........");
		Customer customer=new Customer(12, "test","test@mail.com","123456789");
		kafkaTemplate.send("learning-kafka-cust",customer);
		logger.info("test consume event execution completed.........");
	}

}
