package com.huashui.dormitory.controller.admin;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.DormBuildingUpdateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.DormBuildingPageDTO;
import com.huashui.dormitory.domain.pojo.SysCampus;
import com.huashui.dormitory.domain.vo.BuildingSimpleVO;
import com.huashui.dormitory.domain.vo.DormBuildingDetailVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.DormBuildingPageVO;
import com.huashui.dormitory.service.DormBuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 宿舍楼管理
 */
@Tag(name = "宿舍楼管理")
@RestController
@RequestMapping("/dormitory/admin")
@RequiredArgsConstructor
public class BuildingsController {

    private final DormBuildingService buildingService;

    @GetMapping("/buildings")
    @Operation(summary = "分页列表")
    public Result<PageVO<DormBuildingPageVO>> getBuildingsPage(DormBuildingPageDTO pageDTO) {
        return buildingService.getBuildingsPage(pageDTO);
    }

    @GetMapping("/buildings/{id}")
    @Operation(summary = "详情")
    public Result<DormBuildingDetailVO> getBuildingDetailInfo(@PathVariable Long id) {
        return buildingService.getBuildingDetail(id);
    }

    @PutMapping("/buildings/{id}")
    @Operation(summary = "编辑宿舍楼栋的信息")
    public Result<Void> update(@PathVariable Long id,
                               @RequestBody DormBuildingUpdateDTO dto) {
        return buildingService.updateBuilding(id, dto);
    }

    @GetMapping("/cities")
    @Operation(summary = "查询所有城市下拉列表")
    public Result<List<String>> getCityList() {
        return buildingService.getCityList();
    }

    @GetMapping("/campuses")
    @Operation(summary = "根据城市查询校区下拉列表")
    public Result<List<SysCampus>> getCampusList(@RequestParam(required = false) String city) {
        return buildingService.getCampusList(city);
    }

    @GetMapping("/areas")
    @Operation(summary = "根据校区Id查询片区下拉列表")
    public Result<List<String>> getAreaList(@RequestParam(required = false) Long campusId) {
        return buildingService.getAreaList(campusId);
    }

    @GetMapping("/buildings/codes")
    @Operation(summary = "根据校区查询楼栋编码和名称为下拉列表")
    public Result<List<BuildingSimpleVO>> getBuildingCodeList(@RequestParam(required = false) Long campusId) {
        return buildingService.getBuildingCodeList(campusId);
    }
}