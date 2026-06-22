package com.huashui.client.attendance_api;

import com.huashui.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 考勤服务 Feign 接口
 */
@FeignClient(name = "huashui-attendance", path = "/attendance")
public interface AttendanceFeignClient {

    @GetMapping("/staff/{staffId}/attendance-rate")
    Result<?> getAttendanceRate(@PathVariable Long staffId);
}
