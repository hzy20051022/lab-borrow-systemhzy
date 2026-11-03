package com.lab.listener;

import com.lab.util.JDBCUtils;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            JDBCUtils.getDataSource();
            System.out.println("===== 实验室设备借用管理系统启动成功 =====");
        } catch (Exception e) {
            System.err.println("===== 系统启动失败 =====");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("===== 系统关闭 =====");
    }
}
