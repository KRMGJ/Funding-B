package com.example.funding.dto.response.user;

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
@Schema(name = "MyPageLikedDto", description = "마이페이지 - 좋아요한 프로젝트 조회 Dto")
public class MyPageLikedDto {
    //좋아요 테이블
    @Schema(description = "유저 ID", example = "1")
    private Long userId;
    @Schema(description = "프로젝트 ID", example = "10")
    private Long projectId;
    @Schema(description = "좋아요 생성일", example = "2023-01-01T12:00:00")
    private LocalDateTime createdAt;

    //창작자 테이블
    @Schema(description = "창작자 ID", example = "5")
    private Long creatorId;
    @Schema(description = "창작자 이름", example = "홍길동")
    private String creatorName;

    //프로젝트 테이블
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 가젯")
    private String title;
    @Schema(description = "프로젝트 목표 금액", example = "1000000")
    private Long goalAmount;
    @Schema(description = "프로젝트 현재 금액", example = "500000")
    private Long currAmount;
    @Schema(description = "프로젝트 썸네일 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "프로젝트 종료일", example = "2023-12-31T23:59:59")
    private LocalDateTime endDate;
}
