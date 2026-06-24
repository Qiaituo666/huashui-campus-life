package com.huashui.dormitory.controller.admin;

import com.huashui.dormitory.domain.dto.pageQuery.RoomPageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 */

@Tag(name = "房间管理")
@RestController
@RequestMapping("/dormitory/admin/rooms")
public class RoomsController {

    @Operation(summary = "房间分页列表,必须提供楼栋Id和所在楼层")
    @GetMapping
    public void pageRooms(RoomPageDTO dto) {

        // todo 定义VO
    }

    @Operation(summary = "房间详情（含床位分配情况,和每一个学生的详细信息）")
    @GetMapping("/{id}")
    public void getRoomDetail(@PathVariable Long id) {

    }

    @Operation(summary = "新增房间")
    @PostMapping
    public void createRoom() {
    }

    @Operation(summary = "编辑房间")
    @PutMapping("/{id}")
    public void updateRoom(@PathVariable Long id) {
    }

    @Operation(summary = "删除房间（需检查是否有人入住）")
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
    }

    @Operation(summary = "批量生成房间（按楼栋楼层/房间数）")
    @PostMapping("/buildings/{buildingId}/rooms/batch")
    public void batchGenerateRooms(@PathVariable Long buildingId) {
    }
}