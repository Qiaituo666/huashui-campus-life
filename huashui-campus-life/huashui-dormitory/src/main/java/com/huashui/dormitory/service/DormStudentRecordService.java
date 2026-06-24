package com.huashui.dormitory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.pojo.DormStudentRecord;
import com.huashui.dormitory.domain.vo.RepairOrderSimpleVO;
import com.huashui.dormitory.domain.vo.RepairOrderVO;
import com.huashui.dormitory.domain.vo.myDormitoryInfoVO;

import java.util.List;


public interface DormStudentRecordService extends IService<DormStudentRecord> {

    Result<myDormitoryInfoVO> getMyDormitoryInfo();

    Result<List<RepairOrderSimpleVO>> getMyDormitoryRepairsList();

    Result<RepairOrderVO> getMyDormitoryRepairs(Long id);
}