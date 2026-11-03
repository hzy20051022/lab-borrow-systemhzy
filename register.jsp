<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册 - 校园实验室设备借用管理系统</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: "Microsoft YaHei", Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .box { background: white; border-radius: 12px; padding: 40px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3); width: 400px; }
        .box h1 { text-align: center; color: #333; margin-bottom: 25px; font-size: 22px; }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; margin-bottom: 6px; color: #555; font-size: 14px; }
        .form-group input { width: 100%; padding: 10px; border: 1px solid #ddd;
            border-radius: 6px; font-size: 14px; transition: border-color .2s; }
        .form-group input:focus { outline: none; border-color: #667eea; }
        .btn { width: 100%; padding: 12px; background: #667eea; color: white;
            border: none; border-radius: 6px; font-size: 16px; cursor: pointer; margin-top: 8px; }
        .btn:hover { background: #5a67d8; }
        .msg { color: #e74c3c; font-size: 14px; text-align: center; margin-bottom: 15px; }
        .links { text-align: center; margin-top: 18px; font-size: 14px; }
        .links a { color: #667eea; text-decoration: none; }
    </style>
</head>
<body>
    <div class="box">
        <h1>用户注册</h1>

        <% if (request.getAttribute("msg") != null) { %>
            <div class="msg"><%= request.getAttribute("msg") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-group">
                <label>用户名 *</label>
                <input type="text" name="username" required>
            </div>
            <div class="form-group">
                <label>密码 *</label>
                <input type="password" name="password" required>
            </div>
            <div class="form-group">
                <label>真实姓名 *</label>
                <input type="text" name="realname" required>
            </div>
            <div class="form-group">
                <label>邮箱</label>
                <input type="email" name="email">
            </div>
            <div class="form-group">
                <label>手机号</label>
                <input type="text" name="phone">
            </div>
            <button type="submit" class="btn">注册</button>
        </form>

        <div class="links">
            已有账号？<a href="${pageContext.request.contextPath}/login">返回登录</a>
        </div>
    </div>
</body>
</html>
