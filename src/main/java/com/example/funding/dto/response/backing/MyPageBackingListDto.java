package com.example.funding.dto.response.backing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "MyPageBackingListDto", description = "마이페이지 후원 내역 DTO")
public class MyPageBackingListDto {
    //프로젝트 테이블
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치 개발 프로젝트")
    private String title;
    @Schema(description = "목표 금액", example = "1000000")
    private Long goalAmount;
    @Schema(description = "현재 모금액", example = "500000")
    private Long currAmount;
    @Schema(description = "프로젝트 종료일", example = "2024-12-31T23:59:59")
    private LocalDateTime endDate;
    @Schema(description = "프로젝트 썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;

    //리워드 테이블
    @Schema(description = "후원한 리워드 목록")
    private List<MyPageBacking_RewardDto> mpBackingList = new ArrayList<>();

    //후원 테이블
    @Schema(description = "후원자 ID", example = "42")
    private Long userId;
    @Schema(description = "후원 ID", example = "1001")
    private Long backingId;
    @Schema(description = "후원 금액", example = "50000")
    private Long amount;
    @Schema(description = "후원 생성 일시", example = "2024-06-15T14:30:00")
    private LocalDateTime createdAt;
    @Schema(description = "후원 상태", example = "ACTIVE")
    private String backingStatus;

    //배송 테이블
    @Schema(description = "배송 상태", example = "SHIPPED")
    private String shippingStatus;

    //창작자 테이블
    @Schema(description = "창작자 이름", example = "홍길동")
    private String creatorName;

}
