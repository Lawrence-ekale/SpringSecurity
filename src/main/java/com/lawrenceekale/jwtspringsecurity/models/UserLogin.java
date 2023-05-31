package com.lawrenceekale.jwtspringsecurity.models;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    @Email(message = "Email should be valid")
    private String email;
    private String password;
}
