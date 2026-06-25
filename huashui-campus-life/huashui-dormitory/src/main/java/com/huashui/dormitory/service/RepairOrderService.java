package com.huashui.dormitory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.AssignRepairDTO;
import com.huashui.dormitory.domain.dto.CompleteRepairDTO;
import com.huashui.dormitory.domain.dto.RateRepairDTO;
import com.huashui.dormitory.domain.dto.RepairCreateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.RepairOrderPageQueryDTO;
import com.huashui.dormitory.domain.pojo.RepairOrder;
import com.huashui.dormitory.domain.vo.RepairOrderVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.RepairOrderPageVO;

public interface RepairOrderService extends IService<RepairOrder> {

    Result<Void> createRepair(RepairCreateDTO dto);

    Result<PageVO<RepairOrderPageVO>> pageRepairs(RepairOrderPageQueryDTO dto);

    Result<RepairOrderVO> getRepairDetail(Long id);

    Result<Void> assignRepair(Long id, AssignRepairDTO dto);

    Result<Void> completeRepair(Long id, CompleteRepairDTO dto);

    Result<Void> rateRepair(Long id, RateRepairDTO dto);
}