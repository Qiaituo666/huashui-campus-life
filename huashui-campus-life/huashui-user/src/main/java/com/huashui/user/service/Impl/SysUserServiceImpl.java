package com.huashui.user.service.Impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.exception.BusinessException;
import com.huashui.common.response.Result;
import com.huashui.user.domain.dto.*;
import com.huashui.user.domain.dto.pageQuery.UserPageDTO;
import com.huashui.user.domain.pojo.SysUser;
import com.huashui.user.domain.pojo.SysUserRole;
import com.huashui.user.domain.vo.pageQueryVO.UserDetailVO;
import com.huashui.user.domain.vo.pageQueryVO.UserPageVO;
import com.huashui.user.mapper.SysUserMapper;
import com.huashui.user.service.ISysUserRoleService;
import com.huashui.user.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 - 系统所有用户（学生/保洁/宿管/超级管理员） 服务实现类
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-23
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    /**
     * 用户-角色关联服务，用于读写 sys_user_role 表
     */
    private final ISysUserRoleService userRoleService;

    // ==================== 分页查询 ====================

    /**
     * 分页查询用户列表。
     * <p>
     * 流程：
     * <ol>
     *   <li>根据 DTO 中的筛选条件动态构建 LambdaQueryWrapper</li>
     *   <li>调用 MyBatis Plus 分页插件执行查询</li>
     *   <li>将实体列表转换为 UserPageVO 分页对象返回</li>
     * </ol>
     */
    @Override
    public Result<PageVO<UserPageVO>> pageUsers(UserPageDTO dto) {
        // 1. 构建动态查询条件：有值才加条件，避免无效过滤
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(org.springframework.util.StringUtils.hasText(dto.getUsername()),
                        SysUser::getUsername, dto.getUsername())   // 用户名模糊
                .like(org.springframework.util.StringUtils.hasText(dto.getRealName()),
                        SysUser::getRealName, dto.getRealName())   // 真实姓名模糊
                .like(org.springframework.util.StringUtils.hasText(dto.getPhone()),
                        SysUser::getPhone, dto.getPhone())         // 手机号模糊
                .eq(dto.getUserType() != null, SysUser::getUserType, dto.getUserType())       // 用户类型精确
                .eq(dto.getCampusId() != null, SysUser::getCampusId, dto.getCampusId())       // 校区精确
                .eq(dto.getCollegeId() != null, SysUser::getCollegeId, dto.getCollegeId())     // 学院精确
                .eq(dto.getStatus() != null, SysUser::getStatus, dto.getStatus())             // 状态精确
                .eq(org.springframework.util.StringUtils.hasText(dto.getGrade()),
                        SysUser::getGrade, dto.getGrade())         // 年级精确
                .orderByDesc(SysUser::getCreateTime);              // 按创建时间倒序

        // 2. 执行分页查询
        Page<SysUser> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        page(page, wrapper);

        // 3. 实体 → VO 转换
        List<UserPageVO> records = page.getRecords().stream()
                .map(this::toPageVO)
                .collect(Collectors.toList());

        // 4. 组装统一分页响应
        PageVO<UserPageVO> pageVO = new PageVO<>();
        pageVO.setTotal(page.getTotal());
        pageVO.setPageNum(page.getCurrent());
        pageVO.setPageSize(page.getSize());
        pageVO.setRecords(records);

        return Result.ok(pageVO);
    }

    // ==================== 查询详情 ====================

    /**
     * 查询用户详情（含角色ID列表）。
     * <p>
     * 流程：
     * <ol>
     *   <li>根据 ID 查用户主表</li>
     *   <li>查 sys_user_role 获取该用户绑定的角色 ID 列表</li>
     *   <li>组装 UserDetailVO 返回（不含密码）</li>
     * </ol>
     */
    @Override
    public Result<UserDetailVO> getUserDetail(Long id) {
        // 1. 查用户基本信息
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 基础字段转换为 VO（不含密码）
        UserDetailVO vo = toDetailVO(user);

        // 3. 查询该用户绑定的角色 ID 列表
        List<Long> roleIds = userRoleService.list(
                        new LambdaQueryWrapper<SysUserRole>()
                                .eq(SysUserRole::getUserId, id))
                .stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
        vo.setRoleIds(roleIds);

        return Result.ok(vo);
    }

    // ==================== 新增用户 ====================

    /**
     * 新增用户（管理员创建）。
     * <p>
     * 流程：
     * <ol>
     *   <li>校验用户名唯一性</li>
     *   <li>使用 BCrypt 加密密码</li>
     *   <li>设置默认状态和创建/更新时间</li>
     *   <li>写入数据库</li>
     * </ol>
     */
    @Override
    public Result<Void> createUser(UserCreateDTO dto) {
        // 1. 用户名唯一性校验
        long count = count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, dto.getUsername()));
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }

        // 2. DTO → 实体，密码 BCrypt 加密
        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(BCrypt.hashpw(dto.getPassword()));   // BCrypt 加密，不可逆
        user.setRealName(dto.getRealName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setAvatar(dto.getAvatar());
        user.setGender(dto.getGender());
        user.setUserType(dto.getUserType());
        user.setCampusId(dto.getCampusId());
        user.setCollegeId(dto.getCollegeId());
        user.setMajor(dto.getMajor());
        user.setGrade(dto.getGrade());

        // 3. 设置默认值：新用户默认正常状态
        user.setStatus(true);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // 4. 保存
        save(user);
        return Result.ok();
    }

    // ==================== 编辑用户 ====================

    /**
     * 编辑用户部分字段。
     * <p>
     * 限制：不允许修改 username、password、userType 三个敏感字段。
     * 使用 MyBatis Plus 的 updateById：只更新非 null 字段，
     * 实现"传什么改什么"的部分更新语义。
     * </p>
     */
    @Override
    public Result<Void> updateUser(Long id, UserUpdateDTO dto) {
        // 1. 校验用户存在
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 构建部分更新实体：只 set 非 null 的字段（部分更新）
        SysUser updateUser = new SysUser();
        updateUser.setId(id);
        if (dto.getRealName() != null) updateUser.setRealName(dto.getRealName());
        if (dto.getPhone() != null) updateUser.setPhone(dto.getPhone());
        if (dto.getEmail() != null) updateUser.setEmail(dto.getEmail());
        if (dto.getAvatar() != null) updateUser.setAvatar(dto.getAvatar());
        if (dto.getGender() != null) updateUser.setGender(dto.getGender());
        if (dto.getCampusId() != null) updateUser.setCampusId(dto.getCampusId());
        if (dto.getCollegeId() != null) updateUser.setCollegeId(dto.getCollegeId());
        if (dto.getMajor() != null) updateUser.setMajor(dto.getMajor());
        if (dto.getGrade() != null) updateUser.setGrade(dto.getGrade());

        // 3. 刷新更新时间
        updateUser.setUpdateTime(LocalDateTime.now());

        // 4. MyBatis Plus updateById：自动忽略 null 字段
        updateById(updateUser);
        return Result.ok();
    }

    // ==================== 删除用户 ====================

    /**
     * 删除用户（逻辑删除）。
     * <p>
     * 依赖 SysUser.isDeleted 字段上的 @TableLogic 注解，
     * removeById 会自动将 isDeleted 设为 1，而非物理删除。
     * </p>
     */
    @Override
    public Result<Void> deleteUser(Long id) {
        // 校验存在
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // MyBatis Plus 逻辑删除：UPDATE SET is_deleted = 1
        removeById(id);
        return Result.ok();
    }

    // ==================== 冻结/解冻 ====================

    /**
     * 冻结或解冻指定用户。
     * <p>
     * 通过 LambdaUpdateWrapper 精确更新 status 和 updateTime 两个字段，
     * 避免全字段更新。
     * </p>
     */
    @Override
    public Result<Void> updateUserStatus(Long id, UserStatusDTO dto) {
        // 1. 校验存在
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 定向更新 status + updateTime
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getId, id)
                .set(SysUser::getStatus, dto.getStatus())          // 0-冻结 / 1-正常
                .set(SysUser::getUpdateTime, LocalDateTime.now());
        update(wrapper);
        return Result.ok();
    }

    // ==================== 分配角色 ====================

    /**
     * 批量分配角色给指定用户。
     * <p>
     * 流程：
     * <ol>
     *   <li>校验用户存在</li>
     *   <li>删除该用户的所有旧角色关联（先清空）</li>
     *   <li>若 roleIds 非空，批量插入新的角色关联（全量替换）</li>
     * </ol>
     * 采用"先删后插"策略，保证最终态与入参一致。
     * </p>
     */
    @Override
    public Result<Void> assignUserRoles(Long id, UserRoleAssignDTO dto) {
        // 1. 校验用户存在
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 删除旧的用户-角色关联（全量清空）
        userRoleService.remove(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, id));

        // 3. 如果传入了新的角色列表，批量写入新关联
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            List<SysUserRole> list = dto.getRoleIds().stream()
                    .map(roleId -> new SysUserRole()
                            .setUserId(id)
                            .setRoleId(roleId))
                    .collect(Collectors.toList());
            userRoleService.saveBatch(list);
        }

        return Result.ok();
    }

    // ==================== VO 转换（内部工具方法） ====================

    /**
     * SysUser → UserPageVO（分页列表用，字段精简，不含密码）
     */
    private UserPageVO toPageVO(SysUser u) {
        UserPageVO vo = new UserPageVO();
        vo.setId(u.getId());
        vo.setUsername(u.getUsername());
        vo.setRealName(u.getRealName());
        vo.setPhone(u.getPhone());
        vo.setEmail(u.getEmail());
        vo.setGender(u.getGender());
        vo.setUserType(u.getUserType());
        vo.setCampusId(u.getCampusId());
        vo.setCollegeId(u.getCollegeId());
        vo.setMajor(u.getMajor());
        vo.setGrade(u.getGrade());
        vo.setStatus(u.getStatus());
        vo.setLastLoginTime(u.getLastLoginTime());
        vo.setCreateTime(u.getCreateTime());
        return vo;
    }

    /**
     * SysUser → UserDetailVO（详情用，全字段但不含密码，默认空角色列表）
     */
    private UserDetailVO toDetailVO(SysUser u) {
        UserDetailVO vo = new UserDetailVO();
        vo.setId(u.getId());
        vo.setUsername(u.getUsername());
        vo.setRealName(u.getRealName());
        vo.setPhone(u.getPhone());
        vo.setEmail(u.getEmail());
        vo.setAvatar(u.getAvatar());
        vo.setGender(u.getGender());
        vo.setUserType(u.getUserType());
        vo.setCampusId(u.getCampusId());
        vo.setCollegeId(u.getCollegeId());
        vo.setMajor(u.getMajor());
        vo.setGrade(u.getGrade());
        vo.setStatus(u.getStatus());
        vo.setLastLoginTime(u.getLastLoginTime());
        vo.setCreateTime(u.getCreateTime());
        vo.setUpdateTime(u.getUpdateTime());
        vo.setRoleIds(Collections.emptyList());
        return vo;
    }
}
