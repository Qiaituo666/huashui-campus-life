package com.huashui.dormitory.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.huashui.dormitory.mapper.RepairOrderMapper;
import com.huashui.dormitory.service.RepairOrderService;
import org.springframework.stereotype.Service;

@Service
public class RepairOrderServiceImpl
        extends ServiceImpl<RepairOrderMapper, RepairOrder>
        implements RepairOrderService {

    @Override
    public Result<Void> createRepair(RepairCreateDTO dto) {
        return null;
    }

    @Override
    public Result<PageVO<RepairOrderPageVO>> pageRepairs(RepairOrderPageQueryDTO dto) {
        return null;
    }

    @Override
    public Result<RepairOrderVO> getRepairDetail(Long id) {
        return null;
    }

    @Override
    public Result<Void> assignRepair(Long id, AssignRepairDTO dto) {
        return null;
    }

    @Override
    public Result<Void> completeRepair(Long id, CompleteRepairDTO dto) {
        return null;
    }

    @Override
    public Result<Void> rateRepair(Long id, RateRepairDTO dto) {
        return null;
    }
}