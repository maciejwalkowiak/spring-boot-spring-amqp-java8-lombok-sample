package consumer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class BookService {
    private final RabbitTemplate rabbitTemplate;
    private final ProducerProperties producerProperties;

    Book save(Book book) {
        rabbitTemplate.convertAndSend(producerProperties.getExchange(),
                                      producerProperties.getRoutingKey(),
                                      book);
        return book;
    }
}
