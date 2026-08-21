package dto;

import entity.Authority;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class RoleRequestDTO {

    @NotBlank(message = "role can't be empty")
    private String role;

    @NotBlank(message = "authorities can't be empty")
    private Set<Authority> authorities;

    public RoleRequestDTO(String role) {
        this.role = role;
    }

    public RoleRequestDTO(String role, Set<Authority> authorities) {
        this.role = role;
        this.authorities = authorities;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
