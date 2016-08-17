package producer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties("consumer")
@Data
@Component
public class ConsumerProperties {
    private String exchange;
    private String routingKey;
    private String queue;
}
