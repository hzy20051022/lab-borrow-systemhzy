package com.lab.servlet;

import com.lab.dao.BorrowDAO;
import com.lab.dao.EquipmentDAO;
import com.lab.dao.UserDAO;
import com.lab.entity.Borrow;
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

@WebServlet("/admin/borrow-audit")
public class BorrowAuditServlet extends HttpServlet {

    private final BorrowDAO borrowDAO = new BorrowDAO();
    private final EquipmentDAO equipmentDAO = new EquipmentDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("loginUser") : null;
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list": listAudit(req, resp); break;
            case "approve": approve(req, resp); break;
            case "reject": reject(req, resp); break;
            case "returned": markReturned(req, resp); break;
            case "delete": delete(req, resp); break;
            default: listAudit(req, resp); break;
        }
    }

    private void listAudit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String statusParam = req.getParameter("status");
        List<Borrow> list;
        if (statusParam != null && !statusParam.isEmpty()) {
            list = borrowDAO.findByStatus(Integer.parseInt(statusParam));
        } else {
            list = borrowDAO.findAll();
        }
        fillDetails(list);
        req.setAttribute("borrowList", list);
        req.setAttribute("pendingCount", borrowDAO.countByStatus(0));
        req.setAttribute("approvedCount", borrowDAO.countByStatus(1));
        req.setAttribute("returnedCount", borrowDAO.countByStatus(4));
        req.getRequestDispatcher("/WEB-INF/jsp/admin/audit.jsp").forward(req, resp);
    }

    private void approve(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Borrow borrow = borrowDAO.findById(id);
        if (borrow != null) {
            borrowDAO.updateStatus(id, 1);
            equipmentDAO.updateStatus(borrow.getEquipmentId(), 1);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/borrow-audit?action=list");
    }

    private void reject(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        borrowDAO.updateStatus(id, 2);
        resp.sendRedirect(req.getContextPath() + "/admin/borrow-audit?action=list");
    }

    private void markReturned(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Borrow borrow = borrowDAO.findById(id);
        if (borrow != null) {
            borrowDAO.markReturned(id);
            equipmentDAO.updateStatus(borrow.getEquipmentId(), 0);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/borrow-audit?action=list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        borrowDAO.delete(id);
        resp.sendRedirect(req.getContextPath() + "/admin/borrow-audit?action=list");
    }

    private void fillDetails(List<Borrow> list) {
        if (list == null) return;
        for (Borrow b : list) {
            b.setUser(userDAO.findById(b.getUserId()));
            b.setEquipment(equipmentDAO.findById(b.getEquipmentId()));
        }
    }
}
