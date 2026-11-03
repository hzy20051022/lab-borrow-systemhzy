<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>校园实验室设备借用管理系统</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: "Microsoft YaHei", Arial, sans-serif; background: #f5f7fa; color: #333; }
        .navbar { background: linear-gradient(90deg, #667eea 0%, #764ba2 100%); color: white;
            padding: 0 30px; height: 60px; display: flex; align-items: center; justify-content: space-between;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
        .navbar .logo { font-size: 18px; font-weight: bold; }
        .navbar .nav a { color: white; text-decoration: none; margin-left: 20px; font-size: 14px;
            padding: 6px 14px; border-radius: 4px; transition: background .2s; }
        .navbar .nav a:hover, .navbar .nav a.active { background: rgba(255,255,255,0.2); }
        .navbar .user { font-size: 14px; }
        .container { max-width: 1200px; margin: 25px auto; padding: 0 20px; }
        .card { background: white; border-radius: 8px; box-shadow: 0 2px 12px rgba(0,0,0,0.06); padding: 25px; margin-bottom: 20px; }
        .card h2 { font-size: 18px; margin-bottom: 20px; color: #333; border-bottom: 2px solid #667eea; padding-bottom: 10px; }
        .toolbar { margin-bottom: 15px; display: flex; justify-content: space-between; align-items: center; }
        .btn { display: inline-block; padding: 8px 18px; border-radius: 5px; text-decoration: none;
            font-size: 14px; cursor: pointer; border: none; transition: all .2s; }
        .btn-primary { background: #667eea; color: white; }
        .btn-primary:hover { background: #5a67d8; }
        .btn-success { background: #27ae60; color: white; }
        .btn-success:hover { background: #219a52; }
        .btn-danger { background: #e74c3c; color: white; }
        .btn-danger:hover { background: #c0392b; }
        .btn-warning { background: #f39c12; color: white; }
        .btn-warning:hover { background: #d68910; }
        .btn-default { background: #95a5a6; color: white; }
        .btn-sm { padding: 5px 12px; font-size: 12px; }
        table { width: 100%; border-collapse: collapse; font-size: 14px; }
        table th { background: #f8f9fc; padding: 12px; text-align: left; color: #555; font-weight: 600; border-bottom: 2px solid #eee; }
        table td { padding: 12px; border-bottom: 1px solid #f0f0f0; }
        table tr:hover { background: #fafbff; }
        .badge { display: inline-block; padding: 3px 10px; border-radius: 12px; font-size: 12px; }
        .badge-success { background: #d4edda; color: #155724; }
        .badge-warning { background: #fff3cd; color: #856404; }
        .badge-danger { background: #f8d7da; color: #721c24; }
        .badge-info { background: #d1ecf1; color: #0c5460; }
        .badge-secondary { background: #e2e3e5; color: #383d41; }
        .form-group { margin-bottom: 18px; }
        .form-group label { display: block; margin-bottom: 8px; color: #555; font-size: 14px; font-weight: 500; }
        .form-group input, .form-group select, .form-group textarea { width: 100%; padding: 10px;
            border: 1px solid #ddd; border-radius: 5px; font-size: 14px; transition: border-color .2s; font-family: inherit; }
        .form-group input:focus, .form-group select:focus, .form-group textarea:focus { outline: none; border-color: #667eea; }
        .stats { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 25px; }
        .stat-box { background: white; border-radius: 8px; padding: 25px; box-shadow: 0 2px 12px rgba(0,0,0,0.06); }
        .stat-box .num { font-size: 36px; font-weight: bold; color: #667eea; }
        .stat-box .label { color: #888; font-size: 14px; margin-top: 5px; }
        .msg { padding: 10px 15px; border-radius: 5px; margin-bottom: 15px; font-size: 14px; }
        .msg-error { background: #fdecea; color: #e74c3c; border: 1px solid #f5c6cb; }
        .search-form { display: flex; gap: 10px; }
        .search-form input { padding: 8px 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; width: 250px; }
        .empty { text-align: center; padding: 40px; color: #999; }
    </style>
</head>
<body>
