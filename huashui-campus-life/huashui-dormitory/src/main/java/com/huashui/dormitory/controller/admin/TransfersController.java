package com.huashui.dormitory.controller.admin;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.TransferCreateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.TransferPageQueryDTO;
import com.huashui.dormitory.domain.vo.MyTransferVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.TransferPageVO;
import com.huashui.dormitory.service.DormTransferRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 调宿管理
 */
@Tag(name = "调宿管理")
@RestController
@RequestMapping("/dormitory")
@RequiredArgsConstructor
public class TransfersController {

    private final DormTransferRecordService transferService;

    // ========== 管理端 ==========

    @GetMapping("/admin/transfers")
    @Operation(summary = "调宿申请列表（管理员）")
    public Result<PageVO<TransferPageVO>> pageTransfers(TransferPageQueryDTO dto) {
        return transferService.pageTransfers(dto);
    }

    @PutMapping("/admin/transfers/{id}/approve")
    @Operation(summary = "审批通过调宿申请（自动执行换宿）")
    public Result<Void> approveTransfer(@PathVariable Long id) {
        return transferService.approveTransfer(id);
    }

    @PutMapping("/admin/transfers/{id}/reject")
    @Operation(summary = "驳回调宿申请")
    public Result<Void> rejectTransfer(@PathVariable Long id,
                                       @RequestParam(required = false) String remark) {
        return transferService.rejectTransfer(id, remark);
    }

    // ========== 学生端 ==========

    @PostMapping("/my/transfer")
    @Operation(summary = "学生提交调宿申请")
    public Result<Void> createMyTransfer(@RequestBody TransferCreateDTO dto) {
        return transferService.createMyTransfer(dto);
    }

    @GetMapping("/my/transfer")
    @Operation(summary = "查看我的调宿申请状态")
    public Result<MyTransferVO> getMyTransfer() {
        return transferService.getMyTransfer();
    }
}