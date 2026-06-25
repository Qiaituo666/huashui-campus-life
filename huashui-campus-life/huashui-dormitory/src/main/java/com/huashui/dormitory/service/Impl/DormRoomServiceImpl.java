package com.huashui.dormitory.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.BatchGenerateDTO;
import com.huashui.dormitory.domain.dto.RoomCreateDTO;
import com.huashui.dormitory.domain.dto.RoomUpdateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.RoomPageDTO;
import com.huashui.dormitory.domain.pojo.DormRoom;
import com.huashui.dormitory.domain.vo.RoomDetailVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.RoomPageVO;
import com.huashui.dormitory.mapper.DormRoomMapper;
import com.huashui.dormitory.service.DormRoomService;
import org.springframework.stereotype.Service;

@Service
public class DormRoomServiceImpl
        extends ServiceImpl<DormRoomMapper, DormRoom>
        implements DormRoomService {

    @Override
    public Result<PageVO<RoomPageVO>> pageRooms(RoomPageDTO dto) {
        return null;
    }

    @Override
    public Result<RoomDetailVO> getRoomDetail(Long id) {
        return null;
    }

    @Override
    public Result<Void> createRoom(RoomCreateDTO dto) {
        return null;
    }

    @Override
    public Result<Void> updateRoom(Long id, RoomUpdateDTO dto) {
        return null;
    }

    @Override
    public Result<Void> deleteRoom(Long id) {
        return null;
    }

    @Override
    public Result<Void> batchGenerateRooms(Long buildingId, BatchGenerateDTO dto) {
        return null;
    }

    @Override
    public String getRoomNumber(Long roomId) {
        return getById(roomId).getRoomNumber();
    }
}