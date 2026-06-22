package com.huashui.message.consumer;

import com.huashui.common.constants.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 缴费事件消费者
 */
@Slf4j
@Component
public class PayConsumer {

    @RabbitListener(queues = MQConstants.PAY_QUEUE)
    public void handlePayEvent(String message) {
        log.info("[PayConsumer] 收到缴费事件: {}", message);
        // TODO: payment.completed -> 通知学生「缴费成功」
    }
}
