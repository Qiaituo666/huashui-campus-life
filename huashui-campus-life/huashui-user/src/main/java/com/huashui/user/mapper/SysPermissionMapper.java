package com.huashui.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huashui.user.domain.pojo.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限 Mapper
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /** 根据角色 ID 列表查所有权限 */
    List<SysPermission> selectByRoleIds(@Param("roleIds") List<Long> roleIds);
}
