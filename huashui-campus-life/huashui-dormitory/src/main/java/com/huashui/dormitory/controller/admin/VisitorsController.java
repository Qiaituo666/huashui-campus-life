package com.huashui.dormitory.controller.admin;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.VisitorCreateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.VisitorPageQueryDTO;
import com.huashui.dormitory.domain.vo.pageQueryVO.VisitorPageVO;
import com.huashui.dormitory.service.DormVisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 访客管理
 */
@Tag(name = "访客管理")
@RestController
@RequestMapping("/dormitory/visitors")
@RequiredArgsConstructor
public class VisitorsController {

    private final DormVisitorService visitorService;

    @GetMapping
    @Operation(summary = "访客记录列表（分页/筛选）")
    public Result<PageVO<VisitorPageVO>> pageVisitors(VisitorPageQueryDTO dto) {
        return visitorService.pageVisitors(dto);
    }

    @PostMapping
    @Operation(summary = "登记访客")
    public Result<Void> createVisitor(@RequestBody VisitorCreateDTO dto) {
        return visitorService.createVisitor(dto);
    }

    @PutMapping("/{id}/leave")
    @Operation(summary = "记录访客离开时间")
    public Result<Void> leaveVisitor(@PathVariable Long id) {
        return visitorService.leaveVisitor(id);
    }
}