package com.itheima.publisher.testDemo;

import com.itheima.publisher.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.UUID;


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
        for (int i = 0; i < 1000000; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
        }
    }


    @Test
    public void testSendMsg(){

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name","lin");
        hashMap.put("age",18);

        String exchangeName = "simple.queue";
        for (int i = 0; i < 1000000; i++) {
            rabbitTemplate.convertAndSend(exchangeName,hashMap);
        }
    }

    /**
     * 发送消息示例（带 CorrelationData）
     */
    @Test
    public void testSendMsg2() {
        // 交换机名称
        String exchangeName = "itcast.direct";
        // 消息
        String message = "hello, everyone";

        // 创建 CorrelationData
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        // 发送消息
        rabbitTemplate.convertAndSend(
                exchangeName,     // 交换机名
                "blue",         // 路由键
                message,        // 消息内容
                correlationData    // 消息唯一 ID
        );
    }

    @Test
    public void sendPersistentMessage() {

        // 消息
        String message = "hello, everyone";
        // 发送消息
        for (int i = 0; i < 1000000; i++) {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.ROUTING_KEY,
                    message,
                    msg -> {
                        msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
                        return msg;
                    }
            );
        }

    }


    /**
     * 测试路由模式
     */
    @Test
    public void testFanoutExchange1() {
        // 交换机名称
        String queueName = "fanout.queue1";
        // 消息
        String message = "hello, red";
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }




    @Test
    public void testConsumer() {
        // 交换机名称
        String exchangeName = "Consumer.exchange";

        // 消息
        String message = "Consumer.exchange";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName,null, message);
    }


    @Test
    public void test2() {
        // 交换机名称
        String exchangeName = "text.direct";

        // 消息
        String message = "text.exchange";
        for (int i = 0; i < 1000000; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend(exchangeName,"text", message,
                    msg -> {
                msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return msg;
            });
        }

    }


    @Test
    public void test3() {
        // 交换机名称
        String exchangeName = "text.direct";

        // 消息
        String message = "text.exchange";

        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName,"text", message,
                msg -> {
                    msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return msg;
                });


    }



}