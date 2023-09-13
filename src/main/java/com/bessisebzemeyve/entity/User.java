package com.bessisebzemeyve.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "USER_ACCOUNT")
public class User extends EntityBase{
    @Column(name = "USERNAME", length = 255, unique = true)
    private String username;

    @Column(name = "PASSWORD", length = 255)
    private String password;

    @Column(name = "PHONE_NUMBER")
    private String phone_number;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
