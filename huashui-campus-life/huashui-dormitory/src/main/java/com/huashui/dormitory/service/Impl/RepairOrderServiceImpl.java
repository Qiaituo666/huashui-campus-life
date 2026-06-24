package com.huashui.dormitory.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.huashui.dormitory.domain.pojo.RepairOrder;
import com.huashui.dormitory.mapper.RepairOrderMapper;
import com.huashui.dormitory.service.RepairOrderService;
import org.springframework.stereotype.Service;

@Service
public class RepairOrderServiceImpl
        extends ServiceImpl<RepairOrderMapper, RepairOrder>
        implements RepairOrderService {
}