package com.example.funding.dto.response.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "CreatorProjectSummaryDto", description = "크리에이터 프로젝트 요약 정보 DTO")
public class CreatorProjectSummaryDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치 개발")
    private String title;
    @Schema(description = "프로젝트 종료일", example = "2023-12-31T23:59:59")
    private LocalDateTime endDate;
    @Schema(description = "프로젝트 상태", example = "펀딩중")
    private String projectStatus;
}
