package com.example;


import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * RBAC模型实现Security,即通过角色对用户进行分组，在对每个角色进行权限授权就， 进而简化用户权限分配以及管理。 需要的表： user：
 * 用户信息表 保存有的用户id,用户名、密码、账号、状态、salt加盐 role:角色表 user_role:用户角色关联表 permission： 权限表
 * 保存的有权限相关信息 role_permission: 角色与权限管理表
 */
public interface RbacService {

    //用户判断当前请求是否有操作权限
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
