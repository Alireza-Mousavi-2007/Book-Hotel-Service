package dto;

import jakarta.validation.constraints.NotBlank;

public class AuthorityRequestDTO {

    @NotBlank(message = "authority can't be empty")
    private String authority;

    public AuthorityRequestDTO() {
    }

    public AuthorityRequestDTO(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
