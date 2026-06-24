package com.huashui.dormitory.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.huashui.dormitory.domain.pojo.DormRoom;
import com.huashui.dormitory.mapper.DormRoomMapper;
import com.huashui.dormitory.service.DormRoomService;
import org.springframework.stereotype.Service;

@Service
public class DormRoomServiceImpl
        extends ServiceImpl<DormRoomMapper, DormRoom>
        implements DormRoomService {


    //根据roomID查询房间号
    public String getRoomNumber(Long roomId){
         return getById(roomId).getRoomNumber();
    }
}