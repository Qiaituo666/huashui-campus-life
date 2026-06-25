package com.huashui.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huashui.user.domain.pojo.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色 Mapper
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /** 查用户拥有的角色 */
    @Select("""
        SELECT DISTINCT r.* FROM sys_role r
        INNER JOIN sys_user_role ur ON r.id = ur.role_id
        WHERE ur.user_id = #{userId}
          AND r.status = 1 AND r.is_deleted = 0
    """)
    List<SysRole> selectByUserId(@Param("userId") Long userId);
}
