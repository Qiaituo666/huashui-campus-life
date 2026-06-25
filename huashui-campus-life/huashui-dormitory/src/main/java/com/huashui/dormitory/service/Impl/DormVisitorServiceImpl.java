package com.huashui.dormitory.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.VisitorCreateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.VisitorPageQueryDTO;
import com.huashui.dormitory.domain.pojo.DormVisitor;
import com.huashui.dormitory.domain.vo.pageQueryVO.VisitorPageVO;
import com.huashui.dormitory.mapper.DormVisitorMapper;
import com.huashui.dormitory.service.DormVisitorService;
import org.springframework.stereotype.Service;

@Service
public class DormVisitorServiceImpl
        extends ServiceImpl<DormVisitorMapper, DormVisitor>
        implements DormVisitorService {

    @Override
    public Result<PageVO<VisitorPageVO>> pageVisitors(VisitorPageQueryDTO dto) {
        return null;
    }

    @Override
    public Result<Void> createVisitor(VisitorCreateDTO dto) {
        return null;
    }

    @Override
    public Result<Void> leaveVisitor(Long id) {
        return null;
    }
}