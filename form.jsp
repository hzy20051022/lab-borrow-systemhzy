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
            <a href="${pageContext.request.contextPath}/admin/borrow-audit">借用审核</a>
        <% } %>
    </div>
    <div class="user">
        <span><%= loginUser != null ? loginUser.getRealname() : "" %></span>
        <a href="${pageContext.request.contextPath}/logout" style="color:white;margin-left:15px;">退出</a>
    </div>
</div>

<div class="container">
    <div class="card" style="max-width:600px;margin:25px auto;">
        <h2>${equipment != null ? '编辑设备' : '添加设备'}</h2>
        <c:if test="${not empty msg}"><div class="msg msg-error">${msg}</div></c:if>
        <form method="post" action="${equipment != null ? pageContext.request.contextPath.concat('/equipment/update') : pageContext.request.contextPath.concat('/equipment/save')}">
            <c:if test="${equipment != null}"><input type="hidden" name="id" value="${equipment.id}"></c:if>
            <div class="form-group">
                <label>设备名称 *</label>
                <input type="text" name="name" required value="${equipment != null ? equipment.name : ''}">
            </div>
            <div class="form-group">
                <label>类别</label>
                <input type="text" name="category" placeholder="如：电子仪器/计算机/机械设备/光学仪器"
                       value="${equipment != null ? equipment.category : ''}">
            </div>
            <div class="form-group">
                <label>型号</label>
                <input type="text" name="model" value="${equipment != null ? equipment.model : ''}">
            </div>
            <div class="form-group">
                <label>存放位置</label>
                <input type="text" name="location" placeholder="如：3号楼201"
                       value="${equipment != null ? equipment.location : ''}">
            </div>
            <div class="form-group">
                <label>描述</label>
                <textarea name="description" rows="3">${equipment != null ? equipment.description : ''}</textarea>
            </div>
            <div class="form-group">
                <label>状态</label>
                <select name="status">
                    <option value="0" ${equipment == null || equipment.status == 0 ? 'selected' : ''}>空闲</option>
                    <option value="1" ${equipment != null && equipment.status == 1 ? 'selected' : ''}>借出中</option>
                    <option value="2" ${equipment != null && equipment.status == 2 ? 'selected' : ''}>维修中</option>
                </select>
            </div>
            <div style="display:flex;gap:10px;">
                <button type="submit" class="btn btn-primary">保存</button>
                <a href="${pageContext.request.contextPath}/equipment/list" class="btn btn-default">取消</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
