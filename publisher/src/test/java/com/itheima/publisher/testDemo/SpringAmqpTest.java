package com.itheima.publisher.testDemo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 点对点模式
     */
    @Test
    public void testSimpleQueue() {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "hello, spring amqp1";
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }

    /**
     * 测试工作模式
     * @throws InterruptedException
     */
    @Test
    public void testWorkQueue() throws InterruptedException {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "hello, spring amqp2";
        // 发送消息
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message+i);
            Thread.sleep(20);
        }
    }


    /**
     * 测试发布订阅模式
     */
    @Test
    public void testFanoutExchange() {
        // 交换机名称
        String exchangeName = "itcast.fanout";
        // 消息
        String message = "hello, everyone";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }


    /**
     * 测试路由模式
     */
    @Test
    public void testDirectExchange() {
        // 交换机名称
        String exchangeName = "itcast.direct";
        // 消息
        String message = "hello, red";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "blue", message);
    }

    /**
     * 测试主题模式
     */
    @Test
    public void testTopicExchange() {
        // 交换机名称
        String exchangeName = "itcast.topic";
        // 消息
        String message = "china.newsqaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
    }



}