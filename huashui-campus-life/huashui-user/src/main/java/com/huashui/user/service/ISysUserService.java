package com.huashui.user.service;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.user.domain.dto.*;
import com.huashui.user.domain.dto.pageQuery.UserPageDTO;
import com.huashui.user.domain.pojo.SysUser;
import com.huashui.user.domain.vo.pageQueryVO.UserDetailVO;
import com.huashui.user.domain.vo.pageQueryVO.UserPageVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 - 系统所有用户（学生/保洁/宿管/超级管理员） 服务类
 * </p>
 * <p>
 * 继承 MyBatis Plus IService，自带基础 CRUD（getById / save / updateById / removeById 等）。
 * 本接口扩展了管理端所需的业务方法。
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-23
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 分页查询用户列表。
     * 支持按用户名、真实姓名、手机号模糊搜索，按用户类型、校区、学院、状态、年级精确筛选。
     *
     * @param dto 分页参数 + 筛选条件
     * @return 分页结果（password 字段已脱敏）
     */
    Result<PageVO<UserPageVO>> pageUsers(UserPageDTO dto);

    /**
     * 查询用户详情。
     * 除用户基本信息外，还会查询该用户绑定的角色 ID 列表。
     *
     * @param id 用户ID
     * @return 用户详情（不含密码）+ 角色ID列表
     */
    Result<UserDetailVO> getUserDetail(Long id);

    /**
     * 管理员手动创建新用户。
     * <ul>
     *   <li>校验用户名唯一性，重复则抛 BusinessException</li>
     *   <li>密码使用 BCrypt 加密后存储</li>
     *   <li>新用户默认状态为"正常"</li>
     * </ul>
     *
     * @param dto 用户信息
     */
    Result<Void> createUser(UserCreateDTO dto);

    /**
     * 编辑用户部分字段。
     * <p>
     * 限制：username / password / userType 不可通过此接口修改。
     * 采用部分更新语义——只有 dto 中非 null 的字段才会被更新。
     * </p>
     *
     * @param id  用户ID
     * @param dto 允许修改的字段集合
     */
    Result<Void> updateUser(Long id, UserUpdateDTO dto);

    /**
     * 逻辑删除用户。
     * <p>
     * 依赖 SysUser.isDeleted 上的 @TableLogic，
     * 实际执行 UPDATE sys_user SET is_deleted = 1。
     * </p>
     *
     * @param id 用户ID
     */
    Result<Void> deleteUser(Long id);

    /**
     * 冻结（status = 0）或解冻（status = 1）指定用户。
     *
     * @param id  用户ID
     * @param dto status 字段
     */
    Result<Void> updateUserStatus(Long id, UserStatusDTO dto);

    /**
     * 重新分配用户的角色列表。
     * <p>
     * 采用"先删后插"策略：先删除该用户所有旧的角色关联，
     * 再批量插入新的关联，保证最终角色列表与入参完全一致。
     * </p>
     *
     * @param id  用户ID
     * @param dto 新的角色 ID 列表
     */
    Result<Void> assignUserRoles(Long id, UserRoleAssignDTO dto);
}
