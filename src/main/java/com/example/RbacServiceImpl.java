package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    @Autowired
    private PermissionRepository permissionRepository;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        //获取用户认证信息
        System.out.println(authentication.getAuthorities());
        Object principal = authentication.getPrincipal();
        System.out.println(principal.getClass());
        //判断数据是否为空 以及类型是否正确
        if (null != principal && principal instanceof User) {
            String username = ((User) principal).getUsername();
            System.out.println(username);

        }

        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        String method = request.getMethod();
        System.out.println(method);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = false;
        for (GrantedAuthority authority : authorities) {
            String authorityname = authority.getAuthority();
            System.out.println(authority.getAuthority());
            Permission permission = permissionRepository.findByName(authorityname);
            System.out.println(permissionRepository.findByName(authorityname));
            if (null != permission && permission.getMethod().equals(request.getMethod()) && antPathMatcher.match(permission.getUri(), request.getRequestURI())) {
                hasPermission = true;

                break;
            }
        }
        System.out.println(hasPermission);
        return hasPermission;
    }
}
