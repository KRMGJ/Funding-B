package com.example.funding.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "ProjectVerifyListDto", description = "관리자 - 프로젝트 검수 대기 목록 DTO")
public class ProjectVerifyListDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치 개발 프로젝트")
    private String title;
    @Schema(description = "크리에이터 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "카테고리 ID", example = "10")
    private Long ctgrId;
    @Schema(description = "카테고리 이름", example = "기술")
    private String ctgrName;
    @Schema(description = "서브카테고리 ID", example = "101")
    private Long subctgrId;
    @Schema(description = "서브카테고리 이름", example = "웨어러블")
    private String subctgrName;
    @Schema(description = "목표 금액", example = "50000000")
    private Integer goalAmount;
    @Schema(description = "프로젝트 시작일", example = "2024-07-01T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "프로젝트 종료일", example = "2024-08-01T00:00:00")
    private LocalDateTime endDate;
    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "프로젝트 상태", example = "검수대기")
    private String projectStatus;
    @Schema(description = "검수 요청 일시", example = "2024-06-15T14:30:00")
    private LocalDateTime requestedAt;
}
