package com.huashui.dormitory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.dormitory.domain.pojo.DormRoom;


public interface DormRoomService extends IService<DormRoom> {

    //根据roomID查询房间号
    public String getRoomNumber(Long roomId);
}