package com.huashui.dormitory.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.pageQuery.DormBuildingPageDTO;
import com.huashui.dormitory.domain.pojo.DormBuilding;
import com.huashui.dormitory.domain.vo.DormBuildingDetailVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.DormBuildingPageVO;
import com.huashui.dormitory.mapper.DormBuildingMapper;
import com.huashui.dormitory.service.DormBuildingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormBuildingServiceImpl
        extends ServiceImpl<DormBuildingMapper, DormBuilding>
        implements DormBuildingService {


    @Override
    public Result<List<PageVO<DormBuildingPageVO>>> getBuildingsPage(DormBuildingPageDTO pageDTO) {
        return null;
    }

    @Override
    public Result<DormBuildingDetailVO> getBuildingDetail(Long id) {
        return null;
    }
}