package com.huashui.dormitory.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.huashui.client.user.client.UserFeignClient;
import com.huashui.client.user.domain.vo.UserSimpleVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.pojo.DormBed;
import com.huashui.dormitory.domain.pojo.DormStudentRecord;
import com.huashui.dormitory.domain.vo.RepairOrderSimpleVO;
import com.huashui.dormitory.domain.vo.RepairOrderVO;
import com.huashui.dormitory.domain.vo.myDormitoryInfoVO;
import com.huashui.dormitory.mapper.DormStudentRecordMapper;
import com.huashui.dormitory.service.DormBedService;
import com.huashui.dormitory.service.DormRoomService;
import com.huashui.dormitory.service.DormStudentRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DormStudentRecordServiceImpl
        extends ServiceImpl<DormStudentRecordMapper, DormStudentRecord>
        implements DormStudentRecordService {

    @Autowired
    private UserFeignClient userClient;

    @Autowired
    private DormBedService dormBedService;

    @Autowired
    private DormRoomService dormRoomService;


    @Override
    public Result<myDormitoryInfoVO> getMyDormitoryInfo() {
        //获取当前学生的ID
        Long studentId = StpUtil.getLoginIdAsLong();
        //获取我的基本信息
        Result<UserSimpleVO> myInfo = userClient.getUserById(studentId);
        //查询我的宿舍ID
        Long roomId = dormBedService.lambdaQuery().eq(DormBed::getStudentId, studentId).one().getRoomId();

        // todo 继续拼装vo
        return null;
    }

    @Override
    public Result<List<RepairOrderSimpleVO>> getMyDormitoryRepairsList() {
        return null;
    }

    @Override
    public Result<RepairOrderVO> getMyDormitoryRepairs(Long id) {
        return null;
    }

    //查询宿舍室友的基本信息
    private List<myDormitoryInfoVO.RoomMateInfo> getRoomMateInfo(Long roomId){

        //判断宿舍是否只有一人
        Integer occupiedBeds = dormRoomService.getById(roomId).getOccupiedBeds();
        if (occupiedBeds <= 1){
            return List.of(); // 无室友
        }

        //查询室友的id
        List<DormBed> list = dormBedService.lambdaQuery().eq(DormBed::getRoomId, roomId).list();
        List<Long> mateIds = list.stream().map(DormBed::getStudentId).toList();
        List<UserSimpleVO> userSimpleVOS = userClient.getUsersByIds(mateIds.toString()).getData();

        List<myDormitoryInfoVO.RoomMateInfo> myDormitoryInfoVOS     = new ArrayList<>();

        for (UserSimpleVO userSimpleVO : userSimpleVOS) {
            myDormitoryInfoVO.RoomMateInfo roomMateInfo = new myDormitoryInfoVO.RoomMateInfo();
            roomMateInfo.setRealName(userSimpleVO.getRealName());
            roomMateInfo.setAvatar(userSimpleVO.getAvatar());
            roomMateInfo.setBedNumber(dormBedService.lambdaQuery().eq(DormBed::getStudentId,userSimpleVO.getId()).one().getBedNumber());
            myDormitoryInfoVOS.add(roomMateInfo);
        }
        return myDormitoryInfoVOS;

    }



}