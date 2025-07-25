package com.itheima.publisher.testDemo;

import com.itheima.publisher.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;


@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    // 真正消费的队列（死信路由后的目标队列）
    public static final String REAL_QUEUE = "real.queue";
    public static final String REAL_EXCHANGE = "real.exchange";
    public static final String REAL_ROUTING_KEY = "real.routing.key";

    // 延迟队列（设置TTL + 死信路由）
    public static final String DELAY_QUEUE = "delay.queue";
    public static final String DELAY_EXCHANGE = "delay.exchange";
    public static final String DELAY_ROUTING_KEY = "delay.routing.key";

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
        String msg = "主人你好，我是延迟60秒的消息";
        rabbitTemplate.convertAndSend(
                DELAY_EXCHANGE,
                DELAY_ROUTING_KEY,
                msg
        );

        System.out.println("消息发送成功" + msg);
    }


    @Test
    void testPublisherDelayMessage() {
        // 1.创建消息
        String msg = "hello, delayed message";
        // 2.发送消息，利用消息后置处理器添加消息头
        rabbitTemplate.convertAndSend(
               "delay2.direct",
                "delay2",
                msg,
                message -> {
                    message.getMessageProperties().setDelay(5000); // 设置延迟时间
                    return message;
                }
        );
        System.out.println("发送时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
    }



}