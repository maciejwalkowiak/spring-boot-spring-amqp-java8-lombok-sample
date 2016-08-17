package producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import lombok.AllArgsConstructor;

@SpringBootApplication
@EnableConfigurationProperties(ProducerProperties.class)
@AllArgsConstructor
public class RabbitProducerApplication {
    private final ProducerProperties producerProperties;

    public static void main(String[] args) {
        SpringApplication.run(RabbitProducerApplication.class, args);
    }

    @Bean
    Exchange exchange() {
        return ExchangeBuilder.topicExchange(producerProperties.getExchange()).build();
    }

    @Bean
    MessageConverter messageConverter(ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setJsonObjectMapper(objectMapper);
        return jackson2JsonMessageConverter;
    }
}

