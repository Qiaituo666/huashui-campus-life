package com.huashui.dormitory.controller.admin;

/**
 * @author
 */
public class repairsController {


    /*
    *
    * POST   /dormitory/repairs                      # 学生提交报修 → 发 MQ(repair.created)
GET    /dormitory/repairs                      # 报修列表（分页，支持 status/roomId/userId 筛选）
GET    /dormitory/repairs/{id}                 # 报修详情
PUT    /dormitory/repairs/{id}/assign          # 指派维修员 → 发 MQ(repair.assigned)
PUT    /dormitory/repairs/{id}/complete        # 维修完成 → 发 MQ(repair.completed)
PUT    /dormitory/repairs/{id}/rate            # 学生评价（{ rating: 1-5, comment }）
    *
    * */




}
