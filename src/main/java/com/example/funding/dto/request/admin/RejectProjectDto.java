package com.example.funding.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "RejectProjectDto", description = "프로젝트 거절 사유 DTO")
public class RejectProjectDto {
    @Schema(description = "프로젝트 거절 사유", example = "프로젝트 내용이 부적절합니다.")
    private String rejectedReason;
}
