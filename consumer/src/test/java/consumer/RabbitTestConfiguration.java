package consumer;

import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.context.annotation.Configuration;

/**
 * Creates proxy around each class annotated with @{@link org.springframework.amqp.rabbit.annotation.RabbitListener}
 * that can be used to verify incoming messages via {@link org.springframework.amqp.rabbit.test.RabbitListenerTestHarness}.
 *
 * See example in {@link XBookListenerTest}.
 */
@Configuration
@RabbitListenerTest(capture = true)
class RabbitTestConfiguration {
}
