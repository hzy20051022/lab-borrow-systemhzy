package com.lab.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private String password;
    private String realname;
    private String email;
    private String phone;
    private Integer role;
    private LocalDateTime createTime;

    public User() {}

    public User(String username, String password, String realname) {
        this.username = username;
        this.password = password;
        this.realname = realname;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRealname() { return realname; }
    public void setRealname(String realname) { this.realname = realname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public boolean isAdmin() {
        return this.role != null && this.role == 1;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', realname='" + realname + "', role=" + role + "}";
    }
}
