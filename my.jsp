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
        <a href="${pageContext.request.contextPath}/borrow/my" class="active">我的借用</a>
        <% if (loginUser != null && loginUser.isAdmin()) { %>
            <a href="${pageContext.request.contextPath}/admin/borrow-audit">借用审核</a>
        <% } %>
    </div>
    <div class="user">
        <span><%= loginUser != null ? loginUser.getRealname() : "" %></span>
        <a href="${pageContext.request.contextPath}/logout" style="color:white;margin-left:15px;">退出</a>
    </div>
</div>

<div class="container">
    <div class="card">
        <h2>我的借用记录</h2>
        <div class="toolbar">
            <a href="${pageContext.request.contextPath}/equipment/list" class="btn btn-primary">+ 申请新借用</a>
        </div>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>设备名称</th>
                <th>借用日期</th>
                <th>预计归还</th>
                <th>实际归还</th>
                <th>用途</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${borrowList}" var="b">
                <tr>
                    <td>${b.id}</td>
                    <td>${b.equipment.name}</td>
                    <td>${b.borrowDate}</td>
                    <td>${b.expectedReturnDate}</td>
                    <td>${b.actualReturnDate != null ? b.actualReturnDate : '-'}</td>
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
                        <c:if test="${b.canCancel}">
                            <a href="${pageContext.request.contextPath}/borrow/cancel?id=${b.id}" class="btn btn-danger btn-sm"
                               onclick="return confirm('确定撤销此申请？')">撤销</a>
                        </c:if>
                        <c:if test="${!b.canCancel}">-</c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty borrowList}">
                <tr><td colspan="8" class="empty">暂无借用记录</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
