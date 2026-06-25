package com.huashui.dormitory.controller.admin;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.AssignRepairDTO;
import com.huashui.dormitory.domain.dto.CompleteRepairDTO;
import com.huashui.dormitory.domain.dto.RateRepairDTO;
import com.huashui.dormitory.domain.dto.RepairCreateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.RepairOrderPageQueryDTO;
import com.huashui.dormitory.domain.vo.RepairOrderVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.RepairOrderPageVO;
import com.huashui.dormitory.service.RepairOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 报修管理
 */
@Tag(name = "报修管理")
@RestController
@RequestMapping("/dormitory/repairs")
@RequiredArgsConstructor
public class RepairsController {

    private final RepairOrderService repairService;

    @PostMapping
    @Operation(summary = "学生提交报修")
    public Result<Void> createRepair(@RequestBody RepairCreateDTO dto) {
        return repairService.createRepair(dto);
    }

    @GetMapping
    @Operation(summary = "报修列表（分页查询），管理端")
    public Result<PageVO<RepairOrderPageVO>> pageRepairs(RepairOrderPageQueryDTO dto) {
        return repairService.pageRepairs(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "报修详情")
    public Result<RepairOrderVO> getRepairDetail(@PathVariable Long id) {
        return repairService.getRepairDetail(id);
    }

    @PutMapping("/{id}/assign")
    @Operation(summary = "指派维修员")
    public Result<Void> assignRepair(@PathVariable Long id,
                                     @RequestBody AssignRepairDTO dto) {
        return repairService.assignRepair(id, dto);
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "维修完成")
    public Result<Void> completeRepair(@PathVariable Long id,
                                       @RequestBody CompleteRepairDTO dto) {
        return repairService.completeRepair(id, dto);
    }

    @PutMapping("/{id}/rate")
    @Operation(summary = "学生评价维修")
    public Result<Void> rateRepair(@PathVariable Long id,
                                   @RequestBody RateRepairDTO dto) {
        return repairService.rateRepair(id, dto);
    }
}