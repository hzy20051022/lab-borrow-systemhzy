package com.lab.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Borrow implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer userId;
    private Integer equipmentId;
    private LocalDate borrowDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private String purpose;
    private Integer status;
    private LocalDateTime createTime;

    private User user;
    private Equipment equipment;

    public Borrow() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getEquipmentId() { return equipmentId; }
    public void setEquipmentId(Integer equipmentId) { this.equipmentId = equipmentId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public LocalDate getExpectedReturnDate() { return expectedReturnDate; }
    public void setExpectedReturnDate(LocalDate expectedReturnDate) { this.expectedReturnDate = expectedReturnDate; }
    public LocalDate getActualReturnDate() { return actualReturnDate; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Equipment getEquipment() { return equipment; }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }

    public String getStatusText() {
        if (status == null) return "";
        switch (status) {
            case 0: return "待审核";
            case 1: return "已通过";
            case 2: return "已拒绝";
            case 3: return "已撤销";
            case 4: return "已归还";
            default: return "未知状态";
        }
    }

    public boolean isCanCancel() {
        return this.status != null && this.status == 0;
    }

    public boolean canCancel() {
        return isCanCancel();
    }

    @Override
    public String toString() {
        return "Borrow{id=" + id + ", userId=" + userId + ", equipmentId=" + equipmentId + ", status=" + status + "}";
    }
}
