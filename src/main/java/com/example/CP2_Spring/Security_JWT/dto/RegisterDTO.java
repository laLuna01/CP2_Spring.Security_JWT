package com.example.CP2_Spring.Security_JWT.dto;

import com.example.CP2_Spring.Security_JWT.model.UserRole;

public record RegisterDTO(String login, String senha, UserRole role) {
}
