package com.example.funding.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "AdminProjectListDto", description = "관리자 프로젝트 리스트 조회 DTO")
public class AdminProjectListDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치 개발 프로젝트")
    private String title;
    @Schema(description = "목표 금액", example = "5000000")
    private Integer goalAmount;
    @Schema(description = "현재 모금액", example = "2500000")
    private Integer currAmount;
    @Schema(description = "시작 날짜", example = "2024-01-01T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "종료 날짜", example = "2024-06-30T23:59:59")
    private LocalDateTime endDate;
    @Schema(description = "생성 날짜", example = "2023-12-15T12:34:56")
    private LocalDateTime createdAt;
    @Schema(description = "수정 날짜", example = "2024-01-10T14:20:30")
    private LocalDateTime updatedAt;
    @Schema(description = "썸네일 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "프로젝트 상태", example = "ACTIVE")
    private String projectStatus;
    @Schema(description = "후원자 수", example = "150")
    private Integer backerCnt;
    @Schema(description = "좋아요 수", example = "300")
    private Integer likeCnt;
    @Schema(description = "조회 수", example = "1200")
    private Integer viewCnt;

    @Schema(description = "서브 카테고리 이름", example = "테크")
    private String subctgrName;
    @Schema(description = "메인 카테고리 이름", example = "전자기기")
    private String ctgrName;

    @Schema(description = "크리에이터 이름", example = "홍길동")
    private String creatorName;

    //계산 필드
    @Schema(description = "달성률(%)", example = "50")
    private Integer percentNow;
}
