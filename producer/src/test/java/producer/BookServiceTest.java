package producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BookService}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private ProducerProperties producerProperties;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    private Queue queue;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void initQueues() {
        queue = amqpAdmin.declareQueue();
        amqpAdmin.declareBinding(BindingBuilder.bind(queue)
                                               .to(ExchangeBuilder.topicExchange(producerProperties.getExchange())
                                                                  .build())
                                               .with(producerProperties.getRoutingKey())
                                               .noargs());
    }

    @Test
    public void shouldPublishMessage() throws IOException {
        Book book = new Book("title", "subtitle");
        bookService.save(book);

        Message message = rabbitTemplate.receive(queue.getName());
        Book publishedBook = objectMapper.readValue(message.getBody(), Book.class);

        assertThat(publishedBook).isInstanceOf(Book.class)
                                 .isEqualTo(book);
    }
}