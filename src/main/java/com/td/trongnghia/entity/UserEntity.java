package com.td.trongnghia.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Created by TRONGNGHIA on 3/28/2017.
 */
@Entity
@Table(name = "users")
public class UserEntity implements Serializable{
    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name")
    private String name;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

//    @Override
//    public boolean equals(Object obj) {
//        super.equals(obj);
//        return this.userId == ((UserEntity) obj).getUserId();
//    }
}
