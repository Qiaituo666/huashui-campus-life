package com.huashui.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huashui.auth.domain.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
