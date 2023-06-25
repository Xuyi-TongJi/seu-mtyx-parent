package edu.seu.mtyx.common.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitService {

    //  引入操作rabbitmq 的模板
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param exchange  交换机
     * @param routingKey   路由键 -> 路由到哪个队列
     * @param message   消息
     * @return 是否发送成功
     */
    public boolean sendMessage(String exchange,String routingKey, Object message) {
        //  调用发送数据的方法
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        return true;
    }

    /**
     * 发送延迟消息的方法
     * @param exchange  交换机
     * @param routingKey    路由键
     * @param message   消息内容
     * @param delayTime 延迟时间
     * @return 是否发送成功
     */
    public boolean sendDelayMessage(String exchange,String routingKey, Object message, int delayTime) {

        //  在发送消息的时候设置延迟时间
        rabbitTemplate.convertAndSend(exchange, routingKey, message, message1 -> {
            //  设置一个延迟时间
            message1.getMessageProperties().setDelay(delayTime * 1000);
            return message1;
        });
        return true;
    }
}
