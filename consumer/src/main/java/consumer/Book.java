package consumer;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.Value;

@Value
class Book {
    String title;
    Optional<String> subtitle;
    LocalDateTime publishedAt;
}
