package consumer;

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
    static final String LISTENER_ID = "bookListener";

    @RabbitListener(
            id = LISTENER_ID,
            bindings = @QueueBinding(
                    exchange = @Exchange(value = "#{consumerProperties.exchange}", type = "topic"),
                    value = @Queue(value = "#{consumerProperties.queue}"),
                    key = "#{consumerProperties.routingKey}"
            )
    )
    void listen(Book article) {
        LOG.info("Received book: {}", article);
    }
}
