package com.huashui.user.service.Impl;

import com.huashui.user.domain.pojo.SysUser;
import com.huashui.user.mapper.SysUserMapper;
import com.huashui.user.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 - 系统所有用户（学生/保洁/宿管/超级管理员） 服务实现类
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-23
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
