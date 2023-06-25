package edu.seu.mtyx.search.receiver;

import edu.seu.mtyx.common.constant.MqConst;
import edu.seu.mtyx.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import java.io.IOException;

@Component
public class SkuReceiver {

    @Autowired
    private SkuService skuService;

    /**
     *
     * @param skuId sku id 消息体（以Long格式接收）
     * @param message 消息主体
     * @param channel mq和Consumer之间的管道，通过这个管道来进行ack/nack
     * @throws IOException IO异常
     */
    @RabbitListener(bindings = @QueueBinding( // 声明队列和交换机
            value = @Queue(value = MqConst.QUEUE_GOODS_UPPER, durable = "true"), // 声明队列（创建一个队列，队列不存在时也能生效）
            exchange = @Exchange(value = MqConst.EXCHANGE_GOODS_DIRECT), // 声明交换机
            key = {MqConst.ROUTING_GOODS_UPPER} // 声明路由健, 用该健绑定队列和交换机，生产者通过该路由健确定发送到交换机的哪个队列
    ))
    public void upperSku(Long skuId, Message message, Channel channel) throws IOException {
        if (skuId != null) {
            skuService.upperSku(skuId);
        }
        /*
          第一个参数：表示收到的消息的标号
          第二个参数：如果为true表示可以签收多个消息
         */
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_GOODS_LOWER, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_GOODS_DIRECT),
            key = {MqConst.ROUTING_GOODS_LOWER}
    ))
    public void lowerSku(Long skuId, Message message, Channel channel) throws IOException {
        if (skuId != null) {
            skuService.lowerSku(skuId);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
