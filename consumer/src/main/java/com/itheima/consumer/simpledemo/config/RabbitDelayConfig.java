package com.itheima.consumer.simpledemo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitDelayConfig {

    // 真正消费的队列（死信路由后的目标队列）
    public static final String REAL_QUEUE = "real.queue";
    public static final String REAL_EXCHANGE = "real.exchange";
    public static final String REAL_ROUTING_KEY = "real.routing.key";

    // 延迟队列（设置TTL + 死信路由）
    public static final String DELAY_QUEUE = "delay.queue";
    public static final String DELAY_EXCHANGE = "delay.exchange";
    public static final String DELAY_ROUTING_KEY = "delay.routing.key";


    // 死信队列（真实业务队列）
    @Bean
    public Queue realQueue() {
        return QueueBuilder.durable(REAL_QUEUE).build();
    }

    @Bean
    public DirectExchange realExchange() {
        return new DirectExchange(REAL_EXCHANGE);
    }

    @Bean
    public Binding realBinding() {
        return BindingBuilder.bind(realQueue())
                .to(realExchange())
                .with(REAL_ROUTING_KEY);
    }



    /*
     * 延迟队列，设置 TTL 和死信配置
     * x-dead-letter-exchange：死信交换机
     * x-dead-letter-routing-key：死信路由键
     * x-message-ttl：消息过期时间（毫秒）
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE)
                .withArgument("x-dead-letter-exchange", REAL_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", REAL_ROUTING_KEY)
                .withArgument("x-message-ttl", 60000) // 延迟60秒
                .build();
    }

    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue())
                .to(delayExchange())
                .with(DELAY_ROUTING_KEY);
    }


}
