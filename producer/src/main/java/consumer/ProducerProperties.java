package consumer;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("producer")
@Data
class ProducerProperties {
    private String exchange;
    private String routingKey;
}
