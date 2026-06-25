package com.huashui.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.common.response.Result;
import com.huashui.user.domain.dto.RoleFormDTO;
import com.huashui.user.domain.pojo.SysRole;
import com.huashui.user.domain.vo.RoleDetailVO;
import com.huashui.user.domain.vo.RoleVO;

import java.util.List;

/**
 * 角色表 服务类。
 *
 * @author 陈会闯
 * @since 2026-06-25
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 获取所有启用状态的角色列表。
     * <p>
     * 按 sortOrder 升序排列，常用于前端下拉框或角色管理页。
     * </p>
     *
     * @return 角色 VO 列表
     */
    Result<List<RoleVO>> listRoles();

    /**
     * 查询角色详情。
     * <p>
     * 包含角色基本信息 + 绑定的权限 ID 列表 + 拥有该角色的用户数。
     * </p>
     *
     * @param id 角色ID
     * @return 角色详情 VO
     */
    Result<RoleDetailVO> roleDetails(Long id);

    /**
     * 新增/编辑角色（合一共用）。
     * <p>
     * id == null → 新增，roleName/roleCode 必填；
     * id != null → 编辑，roleCode 会被忽略（编码创建后不可改）。
     * </p>
     *
     * @param id  角色ID，null 表示新增
     * @param dto 角色表单数据
     */
    Result<Void> saveRole(Long id, RoleFormDTO dto);

    /**
     * 批量删除角色（逻辑删除）。
     *
     * @param ids 角色ID列表
     */
    Result<Void> deleteRoles(List<Long> ids);
}
