package com.huashui.dormitory.controller.admin;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.BatchGenerateDTO;
import com.huashui.dormitory.domain.dto.RoomCreateDTO;
import com.huashui.dormitory.domain.dto.RoomUpdateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.RoomPageDTO;
import com.huashui.dormitory.domain.vo.RoomDetailVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.RoomPageVO;
import com.huashui.dormitory.service.DormRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 房间管理
 */
@Tag(name = "房间管理")
@RestController
@RequestMapping("/dormitory/admin/rooms")
@RequiredArgsConstructor
public class RoomsController {

    private final DormRoomService roomService;

    @GetMapping
    @Operation(summary = "房间分页列表，必须提供楼栋Id和所在楼层")
    public Result<PageVO<RoomPageVO>> pageRooms(RoomPageDTO dto) {
        return roomService.pageRooms(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "房间详情（含床位分配情况和每一个学生的详细信息）")
    public Result<RoomDetailVO> getRoomDetail(@PathVariable Long id) {
        return roomService.getRoomDetail(id);
    }

    @PostMapping
    @Operation(summary = "新增房间")
    public Result<Void> createRoom(@RequestBody RoomCreateDTO dto) {
        return roomService.createRoom(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "编辑房间")
    public Result<Void> updateRoom(@PathVariable Long id,
                                   @RequestBody RoomUpdateDTO dto) {
        return roomService.updateRoom(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除房间（需检查是否有人入住）")
    public Result<Void> deleteRoom(@PathVariable Long id) {
        return roomService.deleteRoom(id);
    }

    @PostMapping("/buildings/{buildingId}/rooms/batch")
    @Operation(summary = "批量生成房间（按楼栋楼层/房间数）")
    public Result<Void> batchGenerateRooms(@PathVariable Long buildingId,
                                           @RequestBody BatchGenerateDTO dto) {
        return roomService.batchGenerateRooms(buildingId, dto);
    }
}