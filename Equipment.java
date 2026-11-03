package com.lab.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String category;
    private String model;
    private String location;
    private String description;
    private Integer status;
    private LocalDateTime createTime;

    public Equipment() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public boolean isAvailable() {
        return this.status != null && this.status == 0;
    }

    public String getStatusText() {
        if (status == null) return "";
        switch (status) {
            case 0: return "空闲";
            case 1: return "借出中";
            case 2: return "维修中";
            default: return "未知";
        }
    }

    @Override
    public String toString() {
        return "Equipment{id=" + id + ", name='" + name + "', status=" + status + "}";
    }
}
