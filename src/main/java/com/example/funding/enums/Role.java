package com.example.funding.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Role", description = "사용자 역할 Enum")
public enum Role {
    USER, ADMIN
}
