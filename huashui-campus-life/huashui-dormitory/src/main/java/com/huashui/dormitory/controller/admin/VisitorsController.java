package com.huashui.dormitory.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 */

@Tag(name = "访客管理")
@RestController
@RequestMapping("/dormitory/visitors")
public class VisitorsController {

    @Operation(summary = "访客记录列表（分页/筛选）")
    @GetMapping
    public void pageVisitors() {
    }

    @Operation(summary = "登记访客")
    @PostMapping
    public void createVisitor() {
    }

    @Operation(summary = "记录访客离开时间")
    @PutMapping("/{id}/leave")
    public void leaveVisitor(@PathVariable Long id) {
    }
}