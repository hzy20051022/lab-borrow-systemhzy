<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%
    com.lab.entity.User loginUser = (com.lab.entity.User) session.getAttribute("loginUser");
%>
<div class="navbar">
    <div class="logo">🔬 校园实验室设备借用系统</div>
    <div class="nav">
        <a href="${pageContext.request.contextPath}/equipment/list" class="active">设备列表</a>
        <a href="${pageContext.request.contextPath}/borrow/my">我的借用</a>
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
        <h2>设备列表</h2>
        <div class="toolbar">
            <c:if test="<%= loginUser != null && loginUser.isAdmin() %>">
                <a href="${pageContext.request.contextPath}/equipment/add" class="btn btn-primary">+ 添加设备</a>
            </c:if>
            <form class="search-form" method="get" action="${pageContext.request.contextPath}/equipment/list">
                <input type="text" name="search" placeholder="搜索设备名称/类别/位置" value="${param.search}">
                <button type="submit" class="btn btn-primary btn-sm">搜索</button>
            </form>
        </div>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>设备名称</th>
                <th>类别</th>
                <th>型号</th>
                <th>位置</th>
                <th>状态</th>
                <% if (loginUser != null && loginUser.isAdmin()) { %><th>操作</th><% } %>
                <th>借用</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${equipmentList}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.category}</td>
                    <td>${item.model}</td>
                    <td>${item.location}</td>
                    <td>
                        <c:choose>
                            <c:when test="${item.status == 0}"><span class="badge badge-success">空闲</span></c:when>
                            <c:when test="${item.status == 1}"><span class="badge badge-warning">借出中</span></c:when>
                            <c:when test="${item.status == 2}"><span class="badge badge-danger">维修中</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">未知</span></c:otherwise>
                        </c:choose>
                    </td>
                    <% if (loginUser != null && loginUser.isAdmin()) { %>
                    <td>
                        <a href="${pageContext.request.contextPath}/equipment/edit?id=${item.id}" class="btn btn-primary btn-sm">编辑</a>
                        <a href="${pageContext.request.contextPath}/equipment/delete?id=${item.id}" class="btn btn-danger btn-sm"
                           onclick="return confirm('确定删除该设备？')">删除</a>
                    </td>
                    <% } %>
                    <td>
                        <c:if test="${item.available}">
                            <a href="${pageContext.request.contextPath}/borrow/add?equipmentId=${item.id}" class="btn btn-success btn-sm">申请借用</a>
                        </c:if>
                        <c:if test="${!item.available}">
                            <span class="badge badge-secondary">不可借用</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty equipmentList}">
                <tr><td colspan="8" class="empty">暂无设备</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
