package com.huashui.dormitory.controller.admin;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.RepairCreateDTO;
import com.huashui.dormitory.domain.pojo.RepairOrder;
import com.huashui.dormitory.domain.vo.pageQueryVO.RepairOrderPageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 */


@Tag(name = "报修管理")
@RestController
@RequestMapping("/dormitory/repairs")
public class RepairsController {

    @Operation(summary = "学生提交报修")
    @PostMapping
    public void createRepair(RepairCreateDTO dto) {
    }

    @Operation(summary = "报修列表（分页查询）,管理端")
    @GetMapping
    public PageVO<List<RepairOrderPageVO>> pageRepairs() {
        return  null;
    }

    @Operation(summary = "报修详情")
    @GetMapping("/{id}")
    public Result<RepairOrder> getRepairDetail(@PathVariable Long id) {
        return null;
    }

    @Operation(summary = "指派维修员")
    @PutMapping("/{id}/assign")
    public void assignRepair(@PathVariable Long id) {
    }

    @Operation(summary = "维修完成")
    @PutMapping("/{id}/complete")
    public void completeRepair(@PathVariable Long id) {
    }

    @Operation(summary = "学生评价维修")
    @PutMapping("/{id}/rate")
    public void rateRepair(@PathVariable Long id) {

    }




}
