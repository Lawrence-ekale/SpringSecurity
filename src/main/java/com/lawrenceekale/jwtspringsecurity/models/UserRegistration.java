package com.lawrenceekale.jwtspringsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {
    private String fullname;
    private String email;
    private String password;
}
