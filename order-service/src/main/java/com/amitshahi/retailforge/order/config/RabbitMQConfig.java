package com.amitshahi.retailforge.order.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class RabbitMQConfig {
    private final String exchangeName;

    RabbitMQConfig(@Value("${order.events.exchange}") String exchangeName) {
        this.exchangeName = exchangeName;
    }

    @Bean
    DirectExchange orderExchange() {
        return new DirectExchange(exchangeName, true, false);
    }
}
