package consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness.InvocationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BookListener}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "foo=bar")
public class XBookListenerTest {

    @Autowired
    private ConsumerProperties consumerProperties;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitListenerTestHarness harness;

    @Test
    public void shouldReceiveMessage() throws InterruptedException {
        Book publishedBook = new Book("title", Optional.of("subtitle"), LocalDateTime.now());

        rabbitTemplate.convertAndSend(consumerProperties.getExchange(),
                                      consumerProperties.getRoutingKey(),
                                      publishedBook);

        InvocationData invocationData = harness.getNextInvocationDataFor(BookListener.LISTENER_ID, 10, TimeUnit.SECONDS);
        assertThat(invocationData).isNotNull();
        assertThat(invocationData.getArguments()).isNotNull();
    }
}
