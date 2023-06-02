package com.lawrenceekale.jwtspringsecurity.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {

    @NotEmpty(message = "Name cannot be null")
    private String fullname;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Size(min=8, max=15, message = "Password must be between 8 and 15 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).+$", message = "Password must have at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;
}
