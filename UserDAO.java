package com.lab.dao;

import com.lab.entity.User;
import com.lab.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private final QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRealname(rs.getString("realname"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getInt("role"));
        java.sql.Timestamp timestamp = rs.getTimestamp("create_time");
        if (timestamp != null) {
            user.setCreateTime(timestamp.toLocalDateTime());
        }
        return user;
    }

    public boolean register(User user) {
        String sql = "INSERT INTO user(username, password, realname, email, phone, role) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            int rows = queryRunner.update(sql,
                    user.getUsername(),
                    user.getPassword(),
                    user.getRealname(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getRole() == null ? 0 : user.getRole());
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            return queryRunner.query(sql, rs -> {
                if (rs.next()) return mapUser(rs);
                return null;
            }, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try {
            return queryRunner.query(sql, rs -> {
                if (rs.next()) return mapUser(rs);
                return null;
            }, username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        try {
            Long count = queryRunner.query(sql, new ScalarHandler<>(), username);
            return count != null && count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findById(Integer id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try {
            return queryRunner.query(sql, rs -> {
                if (rs.next()) return mapUser(rs);
                return null;
            }, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
