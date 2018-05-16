# Spring Boot + Spring AMQP + Java 8 Optional and Dates + Lombok Sample

Sample RabbitMQ producer and consumer application with minimum setup.

Project contains two submodules:

- **producer** - with an example how to send message to RabbitMQ.
- **consumer** - with an example how to retrieve message from RabbitMQ.

Both projects use [Lombok](https://projectlombok.org) to minimise the boilerplate.

## Hints

- in order to enable Jackson to (de)serialize `java.util.Optional` following dependency has to be added:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jdk8</artifactId>
</dependency>
```

- in order to enable Jackson to (de)serialize Java 8 date formats following dependency has to be added:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
</dependency>
```

- to keep objects containing data passed in a message immutable, they annotated with Lombok `@Value`.
Since Lombok version 1.16.20, you need to explicitly instruct it to generate `@ConstructorProperties` by adding following line to `lombok.config` file:

```
lombok.anyConstructor.suppressConstructorProperties=false
```

- by default **Spring AMQP** does not utilize Spring Boot configured Jackson `ObjectMapper`. To take the advantage of Jackson autoconfiguration you need to add following bean:

```java
@Bean
MessageConverter messageConverter(ObjectMapper objectMapper) {
    return new Jackson2JsonMessageConverter(objectMapper);
}
```

- to test reliably if message gets send and received, in integration tests you should use anonymous queues so that each listener in each concurrently running application context has its own queue and listeners do not hijack messages from each other causing tests to fail randomly.
This is why it's a good idea to externalise properties like *queue name*, *routing key*, *exchange* to `@ConfigurationProperties` classes
- integration testing of the listener should only cover if message gets received and if it's deserialized properly. Testing the logic should not be a part of an integration test
- to write integration test for a listener following dependency has to be added:

```xml
<dependency>
    <groupId>org.springframework.amqp</groupId>
    <artifactId>spring-rabbit-test</artifactId>
    <version>${spring-amqp.version}</version>
    <scope>test</scope>
</dependency>
```

where `spring-amqp.version` is a property coming from *Spring Boot* parent project - you don't need to specify this version by yourself.

- to take the advantage of `spring-rabbit-test`,  `@RabbitListenerTest(capture = true)` needs to be added to test configuration setup. Check [RabbitTestConfiguration](./consumer/src/test/java/consumer/RabbitTestConfiguration.java)
- check [BookListenerTest](./consumer/src/test/java/consumer/BookListenerTest.java) to see an example integration test.
