package com.huashui.dormitory.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 */

@Tag(name = "调宿管理")
@RestController
@RequestMapping("/dormitory")
public class TransfersController {

    @Operation(summary = "调宿申请列表（管理员）")
    @GetMapping("/admin/transfers")
    public void pageTransfers() {
    }

    @Operation(summary = "审批通过调宿申请（自动执行换宿）")
    @PutMapping("/admin/transfers/{id}/approve")
    public void approveTransfer(@PathVariable Long id) {
    }

    @Operation(summary = "驳回调宿申请")
    @PutMapping("/admin/transfers/{id}/reject")
    public void rejectTransfer(@PathVariable Long id) {
    }

    @Operation(summary = "学生提交调宿申请")
    @PostMapping("/my/transfer")
    public void createMyTransfer() {
    }

    @Operation(summary = "查看我的调宿申请状态")
    @GetMapping("/my/transfer")
    public void getMyTransfer() {
    }
}