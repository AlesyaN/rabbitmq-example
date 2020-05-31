package ru.itis.rabbitmqexample;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqExampleApplication {

    static final String directExchangeName = "image-exchange";

    static final String pngQueueName = "image-png";
    static final String jpgQueueName = "image-jpg";

    static final String pngRoutingKey = "png";
    static final String jpgRoutingKey = "jpg";


    @Bean
    Queue pngQueue() {
        return new Queue(pngQueueName, false);
    }

    @Bean
    Queue jpgQueue() {
        return new Queue(jpgQueueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchangeName);
    }


    @Bean
    Binding jpgBinding(Queue jpgQueue, DirectExchange exchange) {
        return BindingBuilder.bind(jpgQueue).to(exchange).with(jpgRoutingKey);
    }

    @Bean
    Binding pngBinding(Queue pngQueue, DirectExchange exchange) {
        return BindingBuilder.bind(pngQueue).to(exchange).with(pngRoutingKey);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqExampleApplication.class, args);
    }

}
