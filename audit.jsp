<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%
    com.lab.entity.User loginUser = (com.lab.entity.User) session.getAttribute("loginUser");
%>
<div class="navbar">
    <div class="logo">🔬 校园实验室设备借用系统</div>
    <div class="nav">
        <a href="${pageContext.request.contextPath}/equipment/list">设备列表</a>
        <a href="${pageContext.request.contextPath}/borrow/my">我的借用</a>
        <% if (loginUser != null && loginUser.isAdmin()) { %>
            <a href="${pageContext.request.contextPath}/admin/borrow-audit" class="active">借用审核</a>
        <% } %>
    </div>
    <div class="user">
        <span><%= loginUser != null ? loginUser.getRealname() : "" %></span>
        <a href="${pageContext.request.contextPath}/logout" style="color:white;margin-left:15px;">退出</a>
    </div>
</div>

<div class="container">
    <div class="stats">
        <div class="stat-box">
            <div class="num">${pendingCount}</div>
            <div class="label">待审核</div>
        </div>
        <div class="stat-box">
            <div class="num">${approvedCount}</div>
            <div class="label">已借出中</div>
        </div>
        <div class="stat-box">
            <div class="num">${returnedCount}</div>
            <div class="label">已归还</div>
        </div>
    </div>

    <div class="card">
        <h2>借用审核管理</h2>
        <div class="toolbar">
            <div>
                <a href="${pageContext.request.contextPath}/admin/borrow-audit?action=list" class="btn btn-default btn-sm">全部</a>
                <a href="${pageContext.request.contextPath}/admin/borrow-audit?action=list&status=0" class="btn btn-warning btn-sm">待审核</a>
                <a href="${pageContext.request.contextPath}/admin/borrow-audit?action=list&status=1" class="btn btn-success btn-sm">已通过</a>
                <a href="${pageContext.request.contextPath}/admin/borrow-audit?action=list&status=4" class="btn btn-info btn-sm" style="background:#17a2b8">已归还</a>
            </div>
        </div>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>申请人</th>
                <th>设备</th>
                <th>借用日期</th>
                <th>预计归还</th>
                <th>用途</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${borrowList}" var="b">
                <tr>
                    <td>${b.id}</td>
                    <td>${b.user.realname} (${b.user.username})</td>
                    <td>${b.equipment.name}</td>
                    <td>${b.borrowDate}</td>
                    <td>${b.expectedReturnDate}</td>
                    <td>${b.purpose}</td>
                    <td>
                        <c:choose>
                            <c:when test="${b.status == 0}"><span class="badge badge-warning">待审核</span></c:when>
                            <c:when test="${b.status == 1}"><span class="badge badge-success">已通过</span></c:when>
                            <c:when test="${b.status == 2}"><span class="badge badge-danger">已拒绝</span></c:when>
                            <c:when test="${b.status == 3}"><span class="badge badge-secondary">已撤销</span></c:when>
                            <c:when test="${b.status == 4}"><span class="badge badge-info">已归还</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">未知</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${b.status == 0}">
                            <a href="${pageContext.request.contextPath}/admin/borrow-audit?action=approve&id=${b.id}" class="btn btn-success btn-sm">通过</a>
                            <a href="${pageContext.request.contextPath}/admin/borrow-audit?action=reject&id=${b.id}" class="btn btn-danger btn-sm"
                               onclick="return confirm('确定拒绝？')">拒绝</a>
                        </c:if>
                        <c:if test="${b.status == 1}">
                            <a href="${pageContext.request.contextPath}/admin/borrow-audit?action=returned&id=${b.id}" class="btn btn-primary btn-sm">标记归还</a>
                        </c:if>
                        <c:if test="${b.status == 4 || b.status == 2 || b.status == 3}">
                            <a href="${pageContext.request.contextPath}/admin/borrow-audit?action=delete&id=${b.id}" class="btn btn-default btn-sm"
                               onclick="return confirm('确定删除该记录？')">删除</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty borrowList}">
                <tr><td colspan="8" class="empty">暂无记录</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
