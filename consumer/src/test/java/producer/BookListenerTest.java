package producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness.InvocationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BookListener}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RabbitConsumerApplication.class, BookListenerTest.Config.class})
public class BookListenerTest {

    @Configuration
    @RabbitListenerTest(spy = false, capture = true)
    static class Config {
        @Bean
        BookListener bookListener() {
            return new BookListener();
        }
    }

    @Autowired
    private ConsumerProperties consumerProperties;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitListenerTestHarness harness;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReceiveMessage() throws InterruptedException, JsonProcessingException {
        Book publishedBook = new Book("title", Optional.of("subtitle"), LocalDateTime.now());

        System.out.println(objectMapper.writeValueAsString(publishedBook));

        rabbitTemplate.convertAndSend(consumerProperties.getExchange(),
                                      consumerProperties.getRoutingKey(),
                                      publishedBook);

        InvocationData invocationData = harness.getNextInvocationDataFor(BookListener.LISTENER_ID, 10, TimeUnit.SECONDS);
        assertThat(invocationData).isNotNull();
        assertThat(invocationData.getArguments()[0]).isEqualTo(publishedBook);
    }
}