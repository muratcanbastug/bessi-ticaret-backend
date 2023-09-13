package com.bessisebzemeyve.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SaveUserRequestDTO {
    @NotBlank(message = "Please enter username")
    @Size(min = 4, max = 256, message = "Please enter valid name")
    private String userName;

    @NotBlank(message = "Please enter password")
    @Size(min = 4, max = 256, message = "Please enter valid password")
    private String password;

    private String name;
    private String phoneNumber;
    private String address;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
