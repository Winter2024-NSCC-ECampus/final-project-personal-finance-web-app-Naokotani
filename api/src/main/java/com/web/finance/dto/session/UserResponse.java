package com.web.finance.dto.session;

import lombok.Data;

@Data
public class UserResponse {
    private String firstName;
    private String lastName;
    private String address;
    private String landmark;
    private String email;
    private String role;
}
