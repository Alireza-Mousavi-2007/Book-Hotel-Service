package org.bookhotel.bookinghotelsystem.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public class RoleRequestDTO {

    @NotEmpty(message = "role can't be empty")
    private String role;

    @NotEmpty(message = "authorities can't be empty")
    private Set<String> authorities;

    public RoleRequestDTO() {
    }

    public RoleRequestDTO(String role) {
        this.role = role;
    }


    public RoleRequestDTO(String role, Set<String> authorities) {
        this.role = role;
        this.authorities = authorities;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
