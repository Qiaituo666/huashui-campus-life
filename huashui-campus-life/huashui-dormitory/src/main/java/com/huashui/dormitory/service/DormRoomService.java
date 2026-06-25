package com.huashui.dormitory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.BatchGenerateDTO;
import com.huashui.dormitory.domain.dto.RoomCreateDTO;
import com.huashui.dormitory.domain.dto.RoomUpdateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.RoomPageDTO;
import com.huashui.dormitory.domain.pojo.DormRoom;
import com.huashui.dormitory.domain.vo.RoomDetailVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.RoomPageVO;

public interface DormRoomService extends IService<DormRoom> {

    Result<PageVO<RoomPageVO>> pageRooms(RoomPageDTO dto);

    Result<RoomDetailVO> getRoomDetail(Long id);

    Result<Void> createRoom(RoomCreateDTO dto);

    Result<Void> updateRoom(Long id, RoomUpdateDTO dto);

    Result<Void> deleteRoom(Long id);

    Result<Void> batchGenerateRooms(Long buildingId, BatchGenerateDTO dto);

    String getRoomNumber(Long roomId);
}