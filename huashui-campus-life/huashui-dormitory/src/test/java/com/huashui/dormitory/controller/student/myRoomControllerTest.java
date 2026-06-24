package com.huashui.dormitory.controller.student;


import com.huashui.dormitory.domain.pojo.DormBuilding;
import com.huashui.dormitory.service.DormBuildingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

/**
 * @author
 */
@Slf4j
@SpringBootTest
class myRoomControllerTest {

    @Autowired
    private DormBuildingService buildingService;

    @Test
    public void test(){
        DormBuilding byId = buildingService.getById(1L);
        log.info("{}",byId);
    }


}