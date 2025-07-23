package com.itheima.consumer.simpledemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {


    /**
     * 声明交换机
     * @return 交换机类型 FanoutExchange、DirectExchange、TopicExchange
     * 参数一：名称
     * 参数二：是否持久化（存磁盘）
     * 参数三：是否自动删除
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        // return new DirectExchange("itcast.fanout",true,false);
        return new FanoutExchange("itcast.fanout");//不写第二第三参数默认true、false
    }

    /**
     * 	第1个队列
     *  new Queue(xx) 是临时队列
     *  QueueBuilder.durable(MQConstants.HOTEL_DELETE_QUEUE).build() 永久创建队列
     */
    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout.queue1");
        // return QueueBuilder.durable(MQConstants.HOTEL_DELETE_QUEUE).build()
    }

    /**
     * 第2个队列
     */
    @Bean
    public Queue fanoutQueue2(){
        return new Queue("fanout.queue2");
    }



    /**
     * 绑定队列1和交换机
     * 参数一：队列 方法名
     * 参数二：交换机  方法名
     */
    @Bean
    public Binding bindingQueue1(Queue fanoutQueue1, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    /**
     * 绑定队列2和交换机
     * 参数一：队列
     * 参数二：交换机
     */
    @Bean
    public Binding bindingQueue2(Queue fanoutQueue2, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }


}
