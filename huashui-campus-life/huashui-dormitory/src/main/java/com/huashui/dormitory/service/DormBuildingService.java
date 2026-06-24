package com.huashui.dormitory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.DormBuildingPageDTO;
import com.huashui.dormitory.domain.pojo.DormBuilding;
import com.huashui.dormitory.domain.vo.DormBuildingDetailVO;
import com.huashui.dormitory.domain.vo.DormBuildingPageVO;

import java.util.List;


public interface DormBuildingService extends IService<DormBuilding> {
    Result<List<PageVO<DormBuildingPageVO>>> getBuildingsPage(DormBuildingPageDTO pageDTO);

    Result<DormBuildingDetailVO> getBuildingDetail(Long id);
}