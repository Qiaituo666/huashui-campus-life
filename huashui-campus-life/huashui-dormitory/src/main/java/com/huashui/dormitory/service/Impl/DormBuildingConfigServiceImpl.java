package com.huashui.dormitory.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.huashui.dormitory.domain.pojo.DormBuildingConfig;
import com.huashui.dormitory.mapper.DormBuildingConfigMapper;
import com.huashui.dormitory.service.DormBuildingConfigService;
import org.springframework.stereotype.Service;

@Service
public class DormBuildingConfigServiceImpl
        extends ServiceImpl<DormBuildingConfigMapper, DormBuildingConfig>
        implements DormBuildingConfigService {
}