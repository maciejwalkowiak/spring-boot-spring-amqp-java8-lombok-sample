package consumer;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
class Book {
    String title;
    Optional<String> subtitle;
    LocalDateTime publishedAt = LocalDateTime.now();

    Book(String title, String subtitle) {
        this(title, Optional.ofNullable(subtitle));
    }
}
