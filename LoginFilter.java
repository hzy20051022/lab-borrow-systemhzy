package com.lab.filter;

import com.lab.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter({"/equipment/*", "/borrow/*", "/admin/*"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        if (uri.startsWith(contextPath + "/static/")
                || uri.equals(contextPath + "/login")
                || uri.equals(contextPath + "/register")
                || uri.endsWith("login.jsp")
                || uri.endsWith("register.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("loginUser") : null;

        if (user != null) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(contextPath + "/login");
        }
    }

    @Override
    public void destroy() {
    }
}
