package com.web.store.dto.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.web.store.model.Order;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {
    private String firstName;
    private String lastName;
    private String address;
    private String landmark;
    private String email;
    private String role;
}
