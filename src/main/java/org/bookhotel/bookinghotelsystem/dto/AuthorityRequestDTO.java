package org.bookhotel.bookinghotelsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public class AuthorityRequestDTO {

    @NotEmpty(message = "authority can't be empty")
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
