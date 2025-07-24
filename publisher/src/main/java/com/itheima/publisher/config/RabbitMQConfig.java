package com.itheima.publisher.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "my.direct.exchange";
    public static final String QUEUE_NAME = "my.persistent.queue";
    public static final String ROUTING_KEY = "my.key";

    /*
     * 声明交换机
     * 参数一：交换机名称
     * 参数二：是否持久化
     * 参数三：是否自动删除
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    /*
     * 声明队列
     * 参数一：队列名称
     * 参数二：是否持久化
     */
    @Bean
    public Queue persistentQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    // 绑定交换机与队列
    @Bean
    public Binding binding(Queue persistentQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(persistentQueue).to(directExchange).with(ROUTING_KEY);
    }
}