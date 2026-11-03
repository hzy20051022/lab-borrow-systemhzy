<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录 - 校园实验室设备借用管理系统</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: "Microsoft YaHei", Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .login-box { background: white; border-radius: 12px; padding: 40px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3); width: 400px; }
        .login-box h1 { text-align: center; color: #333; margin-bottom: 10px; font-size: 24px; }
        .login-box .subtitle { text-align: center; color: #888; margin-bottom: 30px; font-size: 14px; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; color: #555; font-size: 14px; }
        .form-group input { width: 100%; padding: 12px; border: 1px solid #ddd;
            border-radius: 6px; font-size: 14px; transition: border-color .2s; }
        .form-group input:focus { outline: none; border-color: #667eea; }
        .btn { width: 100%; padding: 12px; background: #667eea; color: white;
            border: none; border-radius: 6px; font-size: 16px; cursor: pointer;
            transition: background .2s; }
        .btn:hover { background: #5a67d8; }
        .msg { color: #e74c3c; font-size: 14px; text-align: center; margin-bottom: 15px; }
        .links { text-align: center; margin-top: 20px; font-size: 14px; }
        .links a { color: #667eea; text-decoration: none; }
        .links a:hover { text-decoration: underline; }
        .success { color: #27ae60; }
    </style>
</head>
<body>
    <div class="login-box">
        <h1>校园实验室设备借用管理系统</h1>
        <p class="subtitle">Lab Equipment Borrow System</p>

        <% if ("register_success".equals(request.getParameter("msg"))) { %>
            <div class="msg success">注册成功，请登录</div>
        <% } %>
        <% if (request.getAttribute("msg") != null) { %>
            <div class="msg"><%= request.getAttribute("msg") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" name="username" required placeholder="请输入用户名">
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" name="password" required placeholder="请输入密码">
            </div>
            <button type="submit" class="btn">登录</button>
        </form>

        <div class="links">
            还没有账号？<a href="${pageContext.request.contextPath}/register">立即注册</a>
        </div>
        <div style="margin-top:15px;font-size:12px;color:#aaa;text-align:center">
            管理员：admin / 123456 | 学生：student / 123456
        </div>
    </div>
</body>
</html>
