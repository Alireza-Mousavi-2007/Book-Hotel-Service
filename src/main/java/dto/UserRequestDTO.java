package dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class UserRequestDTO {

    @NotEmpty(message = "username can't be blank")
    private String username;

    @NotEmpty(message = "email can't be blank")
    @Email(message = "must be in email format")
    private String email;


    @NotEmpty(message = "password can't be blank")
    private String password;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
}
