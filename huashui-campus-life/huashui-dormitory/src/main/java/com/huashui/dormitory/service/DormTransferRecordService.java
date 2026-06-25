package com.huashui.dormitory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.dormitory.domain.dto.TransferCreateDTO;
import com.huashui.dormitory.domain.dto.pageQuery.TransferPageQueryDTO;
import com.huashui.dormitory.domain.pojo.DormTransferRecord;
import com.huashui.dormitory.domain.vo.MyTransferVO;
import com.huashui.dormitory.domain.vo.pageQueryVO.TransferPageVO;

public interface DormTransferRecordService extends IService<DormTransferRecord> {

    Result<PageVO<TransferPageVO>> pageTransfers(TransferPageQueryDTO dto);

    Result<Void> approveTransfer(Long id);

    Result<Void> rejectTransfer(Long id, String remark);

    Result<Void> createMyTransfer(TransferCreateDTO dto);

    Result<MyTransferVO> getMyTransfer();
}