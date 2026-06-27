package com.huashui.user.mapper;

import com.huashui.user.domain.pojo.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色-权限关联表 Mapper 接口
 * </p>
 *
 * @author freedom0213
 * @since 2026-06-23
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

}
