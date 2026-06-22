package com.huashui.message.consumer;

import com.huashui.common.constants.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 请假事件消费者
 */
@Slf4j
@Component
public class LeaveConsumer {

    @RabbitListener(queues = MQConstants.LEAVE_QUEUE)
    public void handleLeaveEvent(String message) {
        log.info("[LeaveConsumer] 收到请假事件: {}", message);
        // TODO: 根据 Routing Key 分发处理：
        //   leave.submitted -> 通知宿管「有新请假申请」
        //   leave.approved  -> 通知申请人「已通过」
        //   leave.rejected  -> 通知申请人「已拒绝」
    }
}
