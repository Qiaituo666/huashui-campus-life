package com.huashui.message.consumer;

import com.huashui.common.constants.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 报修事件消费者
 */
@Slf4j
@Component
public class RepairConsumer {

    @RabbitListener(queues = MQConstants.REPAIR_QUEUE)
    public void handleRepairEvent(String message) {
        log.info("[RepairConsumer] 收到报修事件: {}", message);
        // TODO: 根据 Routing Key 分发处理：
        //   repair.created   -> 通知宿管「有新工单」
        //   repair.assigned  -> 通知维修员「你被派单了」
        //   repair.completed -> 通知学生「维修完成，请评价」
    }
}
