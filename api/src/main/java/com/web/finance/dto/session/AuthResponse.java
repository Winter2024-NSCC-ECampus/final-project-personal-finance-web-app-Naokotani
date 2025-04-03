package com.web.finance.dto.session;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String jwt;


    private List<String> role;
    private String message;
    private Boolean status;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) { this.status = status; }

    public List<String> getRole() { return role; }

    public void setRole(List<String> role) { this.role = role; }
}
