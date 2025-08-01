package com.itheima.consumer.simpledemo;

import com.itheima.config.MqConstants;
import com.itheima.consumer.simpledemo.config.RabbitDelayConfig;
import com.itheima.utils.MultiDelayMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


@Component
public class SpringRabbitListener {

	// 利用RabbitListener来声明要监听的队列信息
    // 将来一旦监听的队列中有了消息，就会推送给当前服务，调用当前方法，处理消息。
    // 可以看到方法体中接收的就是消息体的内容
//     @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueueMessage(String msg) throws InterruptedException {
//        System.out.println("spring 消费者接收到消息：【" + msg + "】");
//        Thread.sleep(200);
//    }

    // 利用RabbitListener来声明要监听的队列信息
//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueueMessage2(Map<String, Object> message) throws InterruptedException {
//        System.out.println(message);
//        Thread.sleep(1000);
//    }

//    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueueMessage2(HashMap<String, Object> hashMap) throws InterruptedException {
        System.out.println(hashMap);
        Thread.sleep(1000);
    }


    //发布订阅模式
//    @RabbitListener(queues = "fanout.queue1")
    public void listenSimpleQueueMessage3(String msg) throws InterruptedException {
        System.out.println("spring 消费者3接收到消息：【" + msg + "】");
    }


    //发布订阅模式
//    @RabbitListener(queues = "fanout.queue2")
    public void listenSimpleQueueMessage4(String msg) throws InterruptedException {
        System.out.println("spring 消费者4接收到消息：【" + msg + "】");
    }



    //路由模式
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "itcast.direct", type = ExchangeTypes.DIRECT),
            arguments = @Argument(name = "x-queue-mode", value = "lazy"),
            key = {"red", "blue"}
    ))
    public void listenSimpleQueueMessage5(String msg) throws InterruptedException {
        System.out.println("spring 消费者5接收到消息：【" + msg + "】");
    }

    //路由模式
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "direct.queue1"),
//            exchange = @Exchange(name = "itcast.direct", type = ExchangeTypes.DIRECT),
//            key = {"blue"}
//    ))
    public void listenSimpleQueueMessage6(String msg) throws InterruptedException {
        System.out.println("spring 消费者6接收到消息：【" + msg + "】");
    }


    //主题模式
//    @RabbitListener(queues = "topic.queue1")
    public void listenSimpleQueueMessage7(String msg) throws InterruptedException {
        System.out.println("spring 消费者7接收到消息：【" + msg + "】");
    }

    //主题模式
//    @RabbitListener(queues = "topic.queue2")
    public void listenSimpleQueueMessage8(String msg) throws InterruptedException {
        System.out.println("spring 消费者8接收到消息：【" + msg + "】");
    }



    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "Consumer.queue"),
            exchange = @Exchange(name = "Consumer.exchange", type = ExchangeTypes.FANOUT)
    ))
    public void listenConsumerQueueMessage(String msg) throws InterruptedException {
        System.out.println("spring 消费者接收到消息：【" + msg + "】");
        throw new RuntimeException("1111111111");

    }



    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(
                name = "text.queue",
                durable = "true",
                arguments = @Argument(name = "x-queue-mode", value = "lazy")
        ),
        exchange = @Exchange(name = "text.direct", type = ExchangeTypes.DIRECT),
        key = "text"
    ))
    public void listentTextQueueMessage(String msg) throws InterruptedException {
        System.out.println("spring 消费者接收到error消息：【" + msg + "】");
    }


    // 延迟队列
    @RabbitListener(queues = RabbitDelayConfig.REAL_QUEUE)
    public void handleMessage(String msg) {
        System.out.println("收到延迟消息：" + msg + "，当前时间：" + LocalDateTime.now());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "delay2.queue", durable = "true"),
            exchange = @Exchange(name = "delay2.direct", delayed = "true"),
            key = "delay2"
    ))
    public void listenDelayMessage(String msg){
        System.out.println("接收到delay2.queue的延迟消息时间" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
    }




}