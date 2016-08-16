package producer;

import java.time.ZonedDateTime;
import java.util.Optional;

import lombok.Value;

@Value
class Book {
    String title;
    Optional<String> subtitle;
    ZonedDateTime publishedAt;
}
