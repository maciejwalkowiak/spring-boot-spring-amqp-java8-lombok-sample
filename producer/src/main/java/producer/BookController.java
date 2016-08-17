package producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
class BookController {
    private final BookService bookService;

    @GetMapping("/book")
    Book book(@RequestParam String title, @RequestParam Optional<String> subtitle) {
        return bookService.save(new Book(title, subtitle));
    }

}
