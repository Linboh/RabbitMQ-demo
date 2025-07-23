package com.itheima.consumer.simpledemo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class SpringRabbitListener {

	// 利用RabbitListener来声明要监听的队列信息
    // 将来一旦监听的队列中有了消息，就会推送给当前服务，调用当前方法，处理消息。
    // 可以看到方法体中接收的就是消息体的内容
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueueMessage(String msg) throws InterruptedException {
        System.out.println("spring 消费者接收到消息：【" + msg + "】");
        Thread.sleep(200);

    }

    // 利用RabbitListener来声明要监听的队列信息
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueueMessage2(String msg) throws InterruptedException {
        System.out.println("spring 消费者2接收到消息：【" + msg + "】");
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



}