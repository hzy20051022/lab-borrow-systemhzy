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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/borrow/*")
public class BorrowServlet extends HttpServlet {

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
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) pathInfo = "/list";
        switch (pathInfo) {
            case "/list": list(req, resp); break;
            case "/my": myList(req, resp); break;
            case "/add": addForm(req, resp); break;
            case "/save": save(req, resp); break;
            case "/cancel": cancel(req, resp); break;
            default: list(req, resp); break;
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Borrow> list = borrowDAO.findAll();
        fillDetails(list);
        req.setAttribute("borrowList", list);
        req.getRequestDispatcher("/WEB-INF/jsp/borrow/list.jsp").forward(req, resp);
    }

    private void myList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("loginUser") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        List<Borrow> list = borrowDAO.findByUserId(user.getId());
        fillDetails(list);
        req.setAttribute("borrowList", list);
        req.getRequestDispatcher("/WEB-INF/jsp/borrow/my.jsp").forward(req, resp);
    }

    private void addForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Equipment> available = equipmentDAO.findAvailable();
        req.setAttribute("availableList", available);
        String equipmentId = req.getParameter("equipmentId");
        if (equipmentId != null && !equipmentId.isEmpty()) {
            req.setAttribute("defaultEquipmentId", equipmentId);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/borrow/form.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("loginUser") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Integer equipmentId = Integer.parseInt(req.getParameter("equipmentId"));
        LocalDate borrowDate = LocalDate.parse(req.getParameter("borrowDate"));
        LocalDate expectedReturnDate = LocalDate.parse(req.getParameter("expectedReturnDate"));
        String purpose = req.getParameter("purpose");

        Equipment eq = equipmentDAO.findById(equipmentId);
        if (eq == null || !eq.isAvailable()) {
            req.setAttribute("msg", "该设备当前不可借用");
            List<Equipment> available = equipmentDAO.findAvailable();
            req.setAttribute("availableList", available);
            req.getRequestDispatcher("/WEB-INF/jsp/borrow/form.jsp").forward(req, resp);
            return;
        }

        Borrow borrow = new Borrow();
        borrow.setUserId(user.getId());
        borrow.setEquipmentId(equipmentId);
        borrow.setBorrowDate(borrowDate);
        borrow.setExpectedReturnDate(expectedReturnDate);
        borrow.setPurpose(purpose);
        borrow.setStatus(0);

        if (borrowDAO.add(borrow)) {
            resp.sendRedirect(req.getContextPath() + "/borrow/my");
        } else {
            req.setAttribute("msg", "申请失败，请稍后再试");
            List<Equipment> available = equipmentDAO.findAvailable();
            req.setAttribute("availableList", available);
            req.getRequestDispatcher("/WEB-INF/jsp/borrow/form.jsp").forward(req, resp);
        }
    }

    private void cancel(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("loginUser") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Integer id = Integer.parseInt(req.getParameter("id"));
        Borrow borrow = borrowDAO.findById(id);
        if (borrow != null && borrow.getUserId().equals(user.getId()) && borrow.canCancel()) {
            borrowDAO.updateStatus(id, 3);
        }
        resp.sendRedirect(req.getContextPath() + "/borrow/my");
    }

    private void fillDetails(List<Borrow> list) {
        if (list == null) return;
        for (Borrow b : list) {
            b.setUser(userDAO.findById(b.getUserId()));
            b.setEquipment(equipmentDAO.findById(b.getEquipmentId()));
        }
    }
}
