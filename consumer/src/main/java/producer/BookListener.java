package producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
class BookListener {
    private static final Logger LOG = LoggerFactory.getLogger(BookListener.class);

    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(value = "spring.amqp.sample", type = "topic"),
                    value = @Queue(value = "book.published", durable = "true"),
                    key = "book"
            )
    )
    void listen(Book article) {
        LOG.info("Received book: {}", article);
    }
}
