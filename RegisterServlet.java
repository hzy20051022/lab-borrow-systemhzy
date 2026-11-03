package com.lab.servlet;

import com.lab.dao.UserDAO;
import com.lab.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String realname = req.getParameter("realname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        if (username == null || password == null || realname == null
                || username.trim().isEmpty() || password.trim().isEmpty() || realname.trim().isEmpty()) {
            req.setAttribute("msg", "用户名、密码、姓名不能为空");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        if (userDAO.existsByUsername(username.trim())) {
            req.setAttribute("msg", "用户名已存在");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password.trim());
        user.setRealname(realname.trim());
        user.setEmail(email != null ? email.trim() : null);
        user.setPhone(phone != null ? phone.trim() : null);
        user.setRole(0);

        if (userDAO.register(user)) {
            resp.sendRedirect(req.getContextPath() + "/login?msg=register_success");
        } else {
            req.setAttribute("msg", "注册失败，请稍后再试");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
