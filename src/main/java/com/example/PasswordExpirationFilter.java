package com.example;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class PasswordExpirationFilter implements Filter {

    private static final long PASSWORD_EXPIRATION_TIME = 30L * 24L * 60L * 60L * 1000L;    // 30 days

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUrl = httpRequest.getRequestURL().toString();

        if (requestUrl.endsWith("png") || requestUrl.endsWith("jpg") || requestUrl.endsWith("css") || requestUrl.endsWith("js")
                || requestUrl.endsWith("bootstrap") || requestUrl.endsWith("/change/password") || requestUrl.endsWith("/new/password")) {
            chain.doFilter(httpRequest, response);
            return;
        }
        System.out.println("PasswordExpirationFilter:" + requestUrl);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = null;

        if (authentication != null) {
            principal = authentication.getPrincipal();
        }
        String username;
        if (null != principal && principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();

            User user = userRepository.getByUsername(username);
            if (user.getPasswordChangedTime() == null) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                String redirectURL = httpRequest.getContextPath() + "/change/password";
                httpResponse.sendRedirect(redirectURL);

            }
            long currentTime = System.currentTimeMillis();
            long lastChangedTime = user.getPasswordChangedTime().getTime();

            if (currentTime > lastChangedTime + PASSWORD_EXPIRATION_TIME) {
                System.out.println("User:" + user.getUsername() + ":password expired");
                System.out.println("Last Time password changed:" + user.getPasswordChangedTime());
                System.out.println("Current Time:" + new Date());

                HttpServletResponse httpResponse = (HttpServletResponse) response;
                String redirectURL = httpRequest.getContextPath() + "/change/password";
                httpResponse.sendRedirect(redirectURL);
            } else {
                System.out.println("User:" + user.getUsername() + ":password Not expired");
                chain.doFilter(request, response);
            }
        } else {

            chain.doFilter(request, response);
        }

    }

}
