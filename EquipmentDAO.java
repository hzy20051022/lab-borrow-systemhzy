package com.lab.dao;

import com.lab.entity.Equipment;
import com.lab.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {

    private final QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

    private Equipment mapEquipment(ResultSet rs) throws SQLException {
        Equipment eq = new Equipment();
        eq.setId(rs.getInt("id"));
        eq.setName(rs.getString("name"));
        eq.setCategory(rs.getString("category"));
        eq.setModel(rs.getString("model"));
        eq.setLocation(rs.getString("location"));
        eq.setDescription(rs.getString("description"));
        eq.setStatus(rs.getInt("status"));
        java.sql.Timestamp timestamp = rs.getTimestamp("create_time");
        if (timestamp != null) {
            eq.setCreateTime(timestamp.toLocalDateTime());
        }
        return eq;
    }

    public boolean add(Equipment eq) {
        String sql = "INSERT INTO equipment(name, category, model, location, description, status) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            int rows = queryRunner.update(sql,
                    eq.getName(),
                    eq.getCategory(),
                    eq.getModel(),
                    eq.getLocation(),
                    eq.getDescription(),
                    eq.getStatus() == null ? 0 : eq.getStatus());
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM equipment WHERE id = ?";
        try {
            int rows = queryRunner.update(sql, id);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Equipment eq) {
        String sql = "UPDATE equipment SET name=?, category=?, model=?, location=?, description=?, status=? WHERE id=?";
        try {
            int rows = queryRunner.update(sql,
                    eq.getName(),
                    eq.getCategory(),
                    eq.getModel(),
                    eq.getLocation(),
                    eq.getDescription(),
                    eq.getStatus(),
                    eq.getId());
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Equipment findById(Integer id) {
        String sql = "SELECT * FROM equipment WHERE id = ?";
        try {
            return queryRunner.query(sql, rs -> {
                if (rs.next()) return mapEquipment(rs);
                return null;
            }, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Equipment> findAll() {
        String sql = "SELECT * FROM equipment ORDER BY id DESC";
        try {
            return queryRunner.query(sql, rs -> {
                List<Equipment> list = new ArrayList<>();
                while (rs.next()) list.add(mapEquipment(rs));
                return list;
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Equipment> findAvailable() {
        String sql = "SELECT * FROM equipment WHERE status = 0 ORDER BY id DESC";
        try {
            return queryRunner.query(sql, rs -> {
                List<Equipment> list = new ArrayList<>();
                while (rs.next()) list.add(mapEquipment(rs));
                return list;
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateStatus(Integer id, Integer status) {
        String sql = "UPDATE equipment SET status = ? WHERE id = ?";
        try {
            int rows = queryRunner.update(sql, status, id);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM equipment";
        try {
            Long count = queryRunner.query(sql, new ScalarHandler<>());
            return count != null ? count : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
