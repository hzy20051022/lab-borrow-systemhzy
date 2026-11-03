package com.lab.dao;

import com.lab.entity.Borrow;
import com.lab.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    private final QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

    private Borrow mapBorrow(ResultSet rs) throws SQLException {
        Borrow b = new Borrow();
        b.setId(rs.getInt("id"));
        b.setUserId(rs.getInt("user_id"));
        b.setEquipmentId(rs.getInt("equipment_id"));
        java.sql.Date d1 = rs.getDate("borrow_date");
        if (d1 != null) b.setBorrowDate(d1.toLocalDate());
        java.sql.Date d2 = rs.getDate("expected_return_date");
        if (d2 != null) b.setExpectedReturnDate(d2.toLocalDate());
        java.sql.Date d3 = rs.getDate("actual_return_date");
        if (d3 != null) b.setActualReturnDate(d3.toLocalDate());
        b.setPurpose(rs.getString("purpose"));
        b.setStatus(rs.getInt("status"));
        java.sql.Timestamp ts = rs.getTimestamp("create_time");
        if (ts != null) b.setCreateTime(ts.toLocalDateTime());
        return b;
    }

    public boolean add(Borrow b) {
        String sql = "INSERT INTO borrow(user_id, equipment_id, borrow_date, expected_return_date, purpose, status) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            java.sql.Date bd = java.sql.Date.valueOf(b.getBorrowDate());
            java.sql.Date rd = java.sql.Date.valueOf(b.getExpectedReturnDate());
            int rows = queryRunner.update(sql,
                    b.getUserId(),
                    b.getEquipmentId(),
                    bd,
                    rd,
                    b.getPurpose(),
                    b.getStatus() == null ? 0 : b.getStatus());
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM borrow WHERE id = ?";
        try {
            int rows = queryRunner.update(sql, id);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(Integer id, Integer status) {
        String sql = "UPDATE borrow SET status = ? WHERE id = ?";
        try {
            int rows = queryRunner.update(sql, status, id);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean markReturned(Integer id) {
        String sql = "UPDATE borrow SET status = 4, actual_return_date = CURDATE() WHERE id = ?";
        try {
            int rows = queryRunner.update(sql, id);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Borrow findById(Integer id) {
        String sql = "SELECT * FROM borrow WHERE id = ?";
        try {
            return queryRunner.query(sql, rs -> {
                if (rs.next()) return mapBorrow(rs);
                return null;
            }, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrow> findAll() {
        String sql = "SELECT * FROM borrow ORDER BY id DESC";
        try {
            return queryRunner.query(sql, rs -> {
                List<Borrow> list = new ArrayList<>();
                while (rs.next()) list.add(mapBorrow(rs));
                return list;
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrow> findByUserId(Integer userId) {
        String sql = "SELECT * FROM borrow WHERE user_id = ? ORDER BY id DESC";
        try {
            return queryRunner.query(sql, rs -> {
                List<Borrow> list = new ArrayList<>();
                while (rs.next()) list.add(mapBorrow(rs));
                return list;
            }, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrow> findByStatus(Integer status) {
        String sql = "SELECT * FROM borrow WHERE status = ? ORDER BY id DESC";
        try {
            return queryRunner.query(sql, rs -> {
                List<Borrow> list = new ArrayList<>();
                while (rs.next()) list.add(mapBorrow(rs));
                return list;
            }, status);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long countByStatus(Integer status) {
        String sql = "SELECT COUNT(*) FROM borrow WHERE status = ?";
        try {
            Long count = queryRunner.query(sql, new ScalarHandler<>(), status);
            return count != null ? count : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
