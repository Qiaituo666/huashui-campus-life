package com.huashui.dormitory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.DormBuildingUpdateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.DormBuildingPageDTO;
import com.huashui.dormitory.domain.pojo.DormBuilding;
import com.huashui.dormitory.domain.pojo.SysCampus;
import com.huashui.dormitory.domain.vo.BuildingSimpleVO;
import com.huashui.dormitory.domain.vo.DormBuildingDetailVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.DormBuildingPageVO;

import java.util.List;

public interface DormBuildingService extends IService<DormBuilding> {
    Result<PageVO<DormBuildingPageVO>> getBuildingsPage(DormBuildingPageDTO pageDTO);

    Result<DormBuildingDetailVO> getBuildingDetail(Long id);

    Result<Void> updateBuilding(Long id, DormBuildingUpdateDTO dto);

    Result<List<String>> getCityList();

    Result<List<SysCampus>> getCampusList(String city);

    Result<List<String>> getAreaList(Long campusId);

    Result<List<BuildingSimpleVO>> getBuildingCodeList(Long campusId);
}