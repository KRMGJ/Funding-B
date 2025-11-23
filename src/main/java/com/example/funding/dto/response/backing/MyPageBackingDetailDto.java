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
@Schema(name = "MyPageBackingDetailDto", description = "마이페이지 후원 상세 정보 DTO")
public class MyPageBackingDetailDto {
    //후원
    @Schema(description = "후원 ID", example = "1")
    private Long backingId;
    @Schema(description = "후원 금액", example = "50000")
    private Long amount;
    @Schema(description = "후원 생성 일시", example = "2024-01-01T12:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "후원 상태", example = "COMPLETED")
    private String backingStatus;

    //후원상세, 리워드
    @Schema(description = "후원 리워드 목록")
    List<MyPageBacking_RewardDto> rewards = new ArrayList<>();

    //결제
    @Schema(description = "결제 방법", example = "CARD")
    private String method;
    @Schema(description = "카드사", example = "VISA")
    private String cardCompany;

    //배송
    @Schema(description = "배송 상태", example = "SHIPPED")
    private String shippingStatus;
    @Schema(description = "운송장 번호", example = "1234567890")
    private String trackingNum;
    @Schema(description = "배송 시작 일시", example = "2024-01-05T15:30:00")
    private LocalDateTime shippedAt;
    @Schema(description = "배송 완료 일시", example = "2024-01-10T10:00:00")
    private LocalDateTime deliveredAt;

    //프로젝트
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치 개발 프로젝트")
    private String title;
    @Schema(description = "프로젝트 썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;

    //주소
    @Schema(description = "주소지 이름", example = "집")
    private String addrName;
    @Schema(description = "수령인", example = "홍길동")
    private String recipient;
    @Schema(description = "우편번호", example = "12345")
    private String postalCode;
    @Schema(description = "도로명 주소", example = "서울특별시 강남구 테헤란로 123")
    private String roadAddr;
    @Schema(description = "상세 주소", example = "101동 202호")
    private String detailAddr;
    @Schema(description = "수령인 전화번호", example = "010-1234-5678")
    private String recipientPhone;

    //창작자
    @Schema(description = "창작자 이름", example = "김창작")
    private String creatorName;
}
