package com.example.funding.dto.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "RecentViewProject", description = "최근 본 프로젝트 정보")
public class RecentViewProject {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "제작자 ID", example = "1")
    private Long creatorId;
    @Schema(description = "제작자 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치")
    private String title;
    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "달성률", example = "75")
    private Integer percentNow;
    @Schema(description = "현재 모금액", example = "1500000")
    private Integer currAmount;
    @Schema(description = "프로젝트 종료일", example = "2024-12-31T23:59:59")
    private LocalDateTime endDate;
}
