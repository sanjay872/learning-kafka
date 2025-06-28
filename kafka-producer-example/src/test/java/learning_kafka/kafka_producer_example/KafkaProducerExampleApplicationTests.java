package learning_kafka.kafka_producer_example;

import learning_kafka.kafka_producer_example.entity.Customer;
import learning_kafka.kafka_producer_example.service.KafkaMessagePublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class KafkaProducerExampleApplicationTests {

	@Container
	static KafkaContainer kafka=new KafkaContainer(DockerImageName.parse("apache/kafka:latest"));

	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry){
		registry.add("spring.kafka.bootstrap-servers",kafka::getBootstrapServers);
	}

	@Autowired
	private KafkaMessagePublisher publisher;

	@Test
	public void testSendEventsToTopic(){
		publisher.publishMessage(new Customer(12, "test","test@mail.com","123456789"));
		await().pollInterval(Duration.ofSeconds(3)).atMost(10, TimeUnit.SECONDS).untilAsserted(()->{

		});
	}
}
