package com.huashui.dormitory.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huashui.dormitory.domain.pojo.DormBed;
import com.huashui.dormitory.mapper.DormBedMapper;
import com.huashui.dormitory.service.DormBedService;
import org.springframework.stereotype.Service;

@Service
public class DormBedServiceImpl
        extends ServiceImpl<DormBedMapper, DormBed>
        implements DormBedService {
}