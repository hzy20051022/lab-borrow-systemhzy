package com.lab.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {

    private static DataSource dataSource;

    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = JDBCUtils.class.getClassLoader()
                    .getResourceAsStream("druid.properties");
            if (inputStream == null) {
                throw new RuntimeException("找不到druid.properties配置文件");
            }
            properties.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            System.out.println("Druid连接池初始化成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Druid连接池初始化失败: " + e.getMessage());
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
