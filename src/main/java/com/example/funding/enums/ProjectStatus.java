package com.example.funding.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProjectStatus", description = "프로젝트 상태 Enum")
public enum ProjectStatus {
    DRAFT,
    VERIFYING,
    UPCOMING,
    REJECTED,
    OPEN,
    SUCCESS,
    FAILED,
    CANCELED,
    SETTLED
}
