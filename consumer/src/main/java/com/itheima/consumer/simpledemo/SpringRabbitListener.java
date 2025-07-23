package com.itheima.consumer.simpledemo;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class SpringRabbitListener {

	// 利用RabbitListener来声明要监听的队列信息
    // 将来一旦监听的队列中有了消息，就会推送给当前服务，调用当前方法，处理消息。
    // 可以看到方法体中接收的就是消息体的内容
//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueueMessage(String msg) throws InterruptedException {
//        System.out.println("spring 消费者接收到消息：【" + msg + "】");
//        Thread.sleep(200);
//
//    }

    // 利用RabbitListener来声明要监听的队列信息
//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueueMessage2(Map<String, Object> message) throws InterruptedException {
//        System.out.println(message);
//        Thread.sleep(1000);
//    }

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueueMessage2(HashMap<String, Object> hashMap) throws InterruptedException {
        System.out.println(hashMap);
        Thread.sleep(1000);
    }


    //发布订阅模式
    @RabbitListener(queues = "fanout.queue1")
    public void listenSimpleQueueMessage3(String msg) throws InterruptedException {
        System.out.println("spring 消费者3接收到消息：【" + msg + "】");
    }


    //发布订阅模式
    @RabbitListener(queues = "fanout.queue2")
    public void listenSimpleQueueMessage4(String msg) throws InterruptedException {
        System.out.println("spring 消费者4接收到消息：【" + msg + "】");
    }



    //路由模式
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "itcast.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenSimpleQueueMessage5(String msg) throws InterruptedException {
        System.out.println("spring 消费者5接收到消息：【" + msg + "】");
    }

    //路由模式
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "itcast.direct", type = ExchangeTypes.DIRECT),
            key = {"blue"}
    ))
    public void listenSimpleQueueMessage6(String msg) throws InterruptedException {
        System.out.println("spring 消费者6接收到消息：【" + msg + "】");
    }


    //主题模式
    @RabbitListener(queues = "topic.queue1")
    public void listenSimpleQueueMessage7(String msg) throws InterruptedException {
        System.out.println("spring 消费者7接收到消息：【" + msg + "】");
    }

    //主题模式
    @RabbitListener(queues = "topic.queue2")
    public void listenSimpleQueueMessage8(String msg) throws InterruptedException {
        System.out.println("spring 消费者8接收到消息：【" + msg + "】");
    }


}