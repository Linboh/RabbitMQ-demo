package com.itheima.publisher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CommonConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取rabbitTemplate
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        // 设置 mandatory 为 true，才能触发 ReturnCallback
        rabbitTemplate.setMandatory(true);

        /*
         * 设置 ConfirmCallback
         * 1. correlationData：消息的唯一标识
         * 2. ack：消息是否成功收到
         * 3. cause：失败的原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
//                System.out.println("消息发送成功");
                log.info("消息发送成功:{}", correlationData);
                // 记录日志
            } else {
                log.info("消息发送失败:{}", cause);
                // 记录日志
            }
            log.info(
                    "correlationData = {},ack = {},cause = {}",
                    correlationData, ack, cause
            );
        });


        /*
         * 设置 ReturnCallback
         * 1. message：投递失败的消息详细信息
         * 2. replyCode：响应码
         * 3. replyText：响应信息
         * 4. exchange：交换机
         * 5. routingKey：路由键
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // 记录日志
            log.info("消息投递失败,replyCode = {},replyText = {},exchange = {},routingKey = {},message = {}"
                    , replyCode, replyText, exchange, routingKey, message.toString());
        });
    }
}