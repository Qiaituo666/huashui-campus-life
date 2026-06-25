package com.huashui.dormitory.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.TransferCreateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.TransferPageQueryDTO;
import com.huashui.dormitory.domain.pojo.DormTransferRecord;
import com.huashui.dormitory.domain.vo.MyTransferVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.TransferPageVO;
import com.huashui.dormitory.mapper.DormTransferRecordMapper;
import com.huashui.dormitory.service.DormTransferRecordService;
import org.springframework.stereotype.Service;

@Service
public class DormTransferRecordServiceImpl
        extends ServiceImpl<DormTransferRecordMapper, DormTransferRecord>
        implements DormTransferRecordService {

    @Override
    public Result<PageVO<TransferPageVO>> pageTransfers(TransferPageQueryDTO dto) {
        return null;
    }

    @Override
    public Result<Void> approveTransfer(Long id) {
        return null;
    }

    @Override
    public Result<Void> rejectTransfer(Long id, String remark) {
        return null;
    }

    @Override
    public Result<Void> createMyTransfer(TransferCreateDTO dto) {
        return null;
    }

    @Override
    public Result<MyTransferVO> getMyTransfer() {
        return null;
    }
}