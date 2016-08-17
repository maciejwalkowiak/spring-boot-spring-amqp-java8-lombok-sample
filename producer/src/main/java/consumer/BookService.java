package consumer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class BookService {
    private final RabbitTemplate rabbitTemplate;

    Book save(Book book) {
        rabbitTemplate.convertAndSend("spring.amqp.sample", "book.published", book);
        return book;
    }
}
