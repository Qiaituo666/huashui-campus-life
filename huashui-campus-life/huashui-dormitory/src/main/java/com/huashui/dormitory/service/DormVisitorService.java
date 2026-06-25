package com.huashui.dormitory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.VisitorCreateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.VisitorPageQueryDTO;
import com.huashui.dormitory.domain.pojo.DormVisitor;
import com.huashui.dormitory.domain.vo.pageQueryVO.VisitorPageVO;

public interface DormVisitorService extends IService<DormVisitor> {

    Result<PageVO<VisitorPageVO>> pageVisitors(VisitorPageQueryDTO dto);

    Result<Void> createVisitor(VisitorCreateDTO dto);

    Result<Void> leaveVisitor(Long id);
}