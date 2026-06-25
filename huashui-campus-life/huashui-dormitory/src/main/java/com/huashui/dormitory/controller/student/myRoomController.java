package com.huashui.dormitory.controller.student;

import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.vo.RepairOrderSimpleVO;
import com.huashui.dormitory.domain.vo.RepairOrderVO;
import com.huashui.dormitory.domain.vo.myDormitoryInfoVO;
import com.huashui.dormitory.service.DormStudentRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生宿舍信息
 */
@Tag(name = "学生宿舍信息")
@RestController
@RequestMapping("/dormitory/my")
@RequiredArgsConstructor
public class myRoomController {

    private final DormStudentRecordService dormStudentService;

    @GetMapping("/room")
    @Operation(summary = "查询我的宿舍的信息（楼栋+房间+室友列表）")
    public Result<myDormitoryInfoVO> getMyDormitoryInfo() {
        return dormStudentService.getMyDormitoryInfo();
    }

    @GetMapping("/repairs")
    @Operation(summary = "查询我的报修记录")
    public Result<List<RepairOrderSimpleVO>> getMyDormitoryRepairs() {
        return dormStudentService.getMyDormitoryRepairsList();
    }

    @GetMapping("/repairs/{id}")
    @Operation(summary = "报修详情（含进度）")
    public Result<RepairOrderVO> getMyDormitoryRepairsById(@PathVariable Long id) {
        return dormStudentService.getMyDormitoryRepairs(id);
    }
}