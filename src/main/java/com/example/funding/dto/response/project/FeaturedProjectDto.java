package com.example.funding.dto.response.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "FeaturedProjectDto", description = "추천 프로젝트 응답 DTO")
public class FeaturedProjectDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치")
    private String title;
    @Schema(description = "창작자 ID", example = "42")
    private Long creatorId;
    @Schema(description = "창작자 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "프로젝트 시작 날짜", example = "2024-01-01T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "프로젝트 종료 날짜", example = "2024-02-01T00:00:00")
    private LocalDateTime endDate;
    @Schema(description = "달성률", example = "75")
    private Integer percentNow;
    @Schema(description = "현재 모금액", example = "750000")
    private Integer currAmount;
    @Schema(description = "점수", example = "4.5")
    private Double score;
}
