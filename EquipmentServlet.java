package com.lab.servlet;

import com.lab.dao.EquipmentDAO;
import com.lab.entity.Equipment;
import com.lab.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/equipment/*")
public class EquipmentServlet extends HttpServlet {

    private final EquipmentDAO equipmentDAO = new EquipmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) pathInfo = "/list";
        switch (pathInfo) {
            case "/list": list(req, resp); break;
            case "/add": addForm(req, resp); break;
            case "/save": save(req, resp); break;
            case "/edit": editForm(req, resp); break;
            case "/update": update(req, resp); break;
            case "/delete": delete(req, resp); break;
            default: list(req, resp); break;
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String search = req.getParameter("search");
        List<Equipment> list;
        if (search != null && !search.trim().isEmpty()) {
            list = equipmentDAO.findAll();
            list.removeIf(e ->
                    (e.getName() == null || !e.getName().toLowerCase().contains(search.toLowerCase()))
                    && (e.getCategory() == null || !e.getCategory().toLowerCase().contains(search.toLowerCase()))
                    && (e.getLocation() == null || !e.getLocation().toLowerCase().contains(search.toLowerCase()))
            );
        } else {
            list = equipmentDAO.findAll();
        }
        req.setAttribute("equipmentList", list);
        req.getRequestDispatcher("/WEB-INF/jsp/equipment/list.jsp").forward(req, resp);
    }

    private void addForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("loginUser") : null;
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/equipment/list");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/jsp/equipment/form.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("loginUser") : null;
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/equipment/list");
            return;
        }
        Equipment eq = buildEquipment(req);
        if (equipmentDAO.add(eq)) {
            resp.sendRedirect(req.getContextPath() + "/equipment/list");
        } else {
            req.setAttribute("msg", "添加失败");
            req.getRequestDispatcher("/WEB-INF/jsp/equipment/form.jsp").forward(req, resp);
        }
    }

    private void editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("loginUser") : null;
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/equipment/list");
            return;
        }
        Integer id = Integer.parseInt(req.getParameter("id"));
        Equipment eq = equipmentDAO.findById(id);
        req.setAttribute("equipment", eq);
        req.getRequestDispatcher("/WEB-INF/jsp/equipment/form.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("loginUser") : null;
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/equipment/list");
            return;
        }
        Equipment eq = buildEquipment(req);
        eq.setId(Integer.parseInt(req.getParameter("id")));
        if (equipmentDAO.update(eq)) {
            resp.sendRedirect(req.getContextPath() + "/equipment/list");
        } else {
            req.setAttribute("msg", "更新失败");
            req.setAttribute("equipment", eq);
            req.getRequestDispatcher("/WEB-INF/jsp/equipment/form.jsp").forward(req, resp);
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("loginUser") : null;
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/equipment/list");
            return;
        }
        Integer id = Integer.parseInt(req.getParameter("id"));
        equipmentDAO.delete(id);
        resp.sendRedirect(req.getContextPath() + "/equipment/list");
    }

    private Equipment buildEquipment(HttpServletRequest req) {
        Equipment eq = new Equipment();
        eq.setName(req.getParameter("name"));
        eq.setCategory(req.getParameter("category"));
        eq.setModel(req.getParameter("model"));
        eq.setLocation(req.getParameter("location"));
        eq.setDescription(req.getParameter("description"));
        String statusStr = req.getParameter("status");
        if (statusStr != null && !statusStr.isEmpty()) {
            eq.setStatus(Integer.parseInt(statusStr));
        } else {
            eq.setStatus(0);
        }
        return eq;
    }
}
