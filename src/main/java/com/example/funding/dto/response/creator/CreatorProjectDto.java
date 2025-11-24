package com.example.funding.dto.response.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "CreatorProjectDto", description = "크리에이터 프로젝트 응답 DTO")
public class CreatorProjectDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "나만의 특별한 굿즈 만들기")
    private String title;
    @Schema(description = "프로젝트 썸네일 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "현재 모금액", example = "500000")
    private Long currAmount;
    @Schema(description = "목표 모금액", example = "1000000")
    private Long goalAmount;
    @Schema(description = "후원자 수", example = "150")
    private Long backerCnt;
    @Schema(description = "프로젝트 상태", example = "OPEN")
    private String projectStatus;
    @Schema(description = "프로젝트 종료일", example = "2024-12-31T23:59:59")
    private LocalDateTime endDate;
    @Schema(description = "프로젝트 성공 여부", example = "true")
    private Boolean isSuccess;
}
