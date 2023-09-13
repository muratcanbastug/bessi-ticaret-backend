package com.bessisebzemeyve.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequestDTO {
    @NotBlank(message = "Please enter new password")
    @Size(min = 4, max = 256, message = "Please enter valid password")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
