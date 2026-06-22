package com.huashui.utility.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 每日用水数据同步（后续迁移至 XXL-JOB）
 */
@Slf4j
@Component
public class DailyWaterSyncJob {

    @Scheduled(cron = "0 5 0 * * ?")
    public void syncWaterUsage() {
        log.info("[WaterSync] 开始同步昨日用水数据...");
        // TODO: 调用 service 同步各房间用水量
    }
}
