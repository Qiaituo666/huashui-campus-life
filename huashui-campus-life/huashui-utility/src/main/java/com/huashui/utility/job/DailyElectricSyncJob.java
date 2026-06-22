package com.huashui.utility.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 每日用电数据同步（后续迁移至 XXL-JOB）
 */
@Slf4j
@Component
public class DailyElectricSyncJob {

    @Scheduled(cron = "0 10 0 * * ?")
    public void syncElectricUsage() {
        log.info("[ElectricSync] 开始同步昨日用电数据...");
        // TODO: 调用 service 同步各房间用电量
    }
}
