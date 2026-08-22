package dto;

import entity.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

import java.util.Set;

public class AdminUserRequestDTO {

    @NotBlank(message = "username can't be blank")
    private String username;

    @NotBlank(message = "email can't be blank")
    @Email(message = "must be in email format")
    private String email;

    @NotBlank(message = "password can't be blank")
    private String password;

    private Set<String> roles;

    public AdminUserRequestDTO() {
    }

    public AdminUserRequestDTO(String username, String email, String password, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
