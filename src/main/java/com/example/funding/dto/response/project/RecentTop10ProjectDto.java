package com.example.funding.dto.response.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "RecentTop10ProjectDto", description = "최근 10개 프로젝트 응답 DTO")
public class RecentTop10ProjectDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치")
    private String title;
    @Schema(description = "프로젝트 썸네일 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "창작자 ID", example = "42")
    private Long creatorId;
    @Schema(description = "창작자 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "현재 모금액", example = "500000")
    private Integer currAmount;
    @Schema(description = "목표 모금액", example = "1000000")
    private Integer goalAmount;
    @Schema(description = "프로젝트 종료일", example = "2024-12-31T23:59:59")
    private LocalDateTime endDate;
    @Schema(description = "달성률(%)", example = "50")
    private Integer percentNow;
    @Schema(description = "트렌드 점수", example = "75.5")
    private Double trendScore;
}
