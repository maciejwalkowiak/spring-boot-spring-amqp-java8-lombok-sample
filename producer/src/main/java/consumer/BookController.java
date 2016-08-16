package consumer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
class BookController {
    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/book")
    Book book(@RequestParam String title, @RequestParam Optional<String> subtitle) {
        Book book = new Book(title, subtitle);
        rabbitTemplate.convertAndSend("spring.amqp", "book", book);
        return book;
    }

}
