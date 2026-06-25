package com.huashui.user.mapper;

import com.huashui.user.domain.pojo.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户-角色关联表 Mapper 接口
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-23
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}
