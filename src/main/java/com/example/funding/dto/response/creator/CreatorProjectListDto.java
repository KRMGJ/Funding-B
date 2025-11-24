package com.example.funding.dto.response.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "CreatorProjectListDto", description = "크리에이터 프로젝트 리스트 응답 DTO")
public class CreatorProjectListDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "나만의 멋진 프로젝트")
    private String title;
    @Schema(description = "목표 금액", example = "1000000")
    private Integer goalAmount;
    @Schema(description = "현재 금액", example = "500000")
    private Integer currAmount;
    @Schema(description = "시작 날짜", example = "2024-01-01T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "종료 날짜", example = "2024-12-31T23:59:59")
    private LocalDateTime endDate;
    @Schema(description = "생성 날짜", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "수정 날짜", example = "2024-06-01T12:00:00")
    private LocalDateTime updatedAt;
    @Schema(description = "썸네일 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "프로젝트 상태", example = "APPROVED")
    private String projectStatus;
    @Schema(description = "후원자 수", example = "150")
    private Integer backerCnt;
    @Schema(description = "좋아요 수", example = "300")
    private Integer likeCnt;
    @Schema(description = "조회 수", example = "1200")
    private Integer viewCnt;
    @Schema(description = "검토 요청 날짜", example = "2024-05-01T10:00:00")
    private LocalDateTime requestedAt;
    @Schema(description = "거절 사유", example = "프로젝트 설명이 부족합니다.")
    private String rejectedReason;

    @Schema(description = "서브 카테고리 이름", example = "테크")
    private String subctgrName;
    @Schema(description = "메인 카테고리 이름", example = "기술")
    private String ctgrName;

    //계산 필드
    @Schema(description = "달성 퍼센트", example = "50")
    private Integer percentNow;

    //프론트
    @Schema(description = "새 소식 수", example = "5")
    private Integer newsCount; // 새 새소식 수
    @Schema(description = "마지막 새소식 작성일", example = "2024-06-15T14:30:00")
    private LocalDateTime lastNewsAt; // 마지막 작성일
    @Schema(description = "새 후기 수", example = "3")
    private Integer reviewNewCount; // 새 후기 수
    @Schema(description = "미답글 수", example = "2")
    private Integer reviewPendingCount; // 미답글 수
    @Schema(description = "마지막 후기 작성일", example = "2024-06-10T11:20:00")
    private LocalDateTime lastReviewAt; // 마지막 작성일
}
