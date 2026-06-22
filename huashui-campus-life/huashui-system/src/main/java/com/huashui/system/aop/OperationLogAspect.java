package com.huashui.system.aop;

import com.huashui.common.annotation.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 操作日志切面
 * <p>
 * 拦截 {@link OperationLog} 注解，记录操作日志入库。
 */
@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    @Around("@annotation(opLog)")
    public Object around(ProceedingJoinPoint joinPoint, OperationLog opLog) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long cost = System.currentTimeMillis() - start;

        log.info("[操作日志] 模块={} 操作={} 耗时={}ms",
                opLog.module(), opLog.action(), cost);
        // TODO: 入库 sys_operation_log 表
        return result;
    }
}
