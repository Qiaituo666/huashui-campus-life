package com.huashui.dormitory.controller.admin;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.DormBuildingPageDTO;
import com.huashui.dormitory.domain.dto.DormBuildingUpdateDTO;
import com.huashui.dormitory.domain.vo.DormBuildingDetailVO;
import com.huashui.dormitory.domain.vo.DormBuildingPageVO;
import com.huashui.dormitory.service.DormBuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "宿舍楼管理")
@RestController
@RequestMapping("/dormitory/admin")
public class buildingsController {

    @Autowired
    private DormBuildingService buildingService;


    @GetMapping("/buildings ")
    @Operation(summary = "分页列表")
    public Result<List<PageVO<DormBuildingPageVO>>> getBuildingsPage(DormBuildingPageDTO pageDTO){
        return buildingService.getBuildingsPage(pageDTO);
    }


    @GetMapping("/buildings/{id}  ")
    @Operation(summary = "详情")
    public Result<DormBuildingDetailVO> getBuildingDetailInfo(@PathVariable Long id){
        return buildingService.getBuildingDetail(id);
    }

    @Operation(summary = "编辑宿舍楼栋的信息")
    @PutMapping("/buildings/{id}")
    public void update(@PathVariable Long id,
                       @RequestBody DormBuildingUpdateDTO dto){
        // todo
    }

    // todo  根据所在城市查询该城市的校区,没有则查询所有校区


    // todo 根据校区,查询该校区的片区,然后下拉菜单用户选择 ,还未选择校区时查询所有片区


    // todo //根据片区查询楼栋编码为下拉菜单

    // todo  根据楼栋的id 查询有空余床位的房间列表

}
