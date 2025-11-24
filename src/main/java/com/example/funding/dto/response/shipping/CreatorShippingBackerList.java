package com.example.funding.dto.response.shipping;

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
@Schema(name = "CreatorShippingBackerList", description = "창작자 배송 후원자 리스트 응답 DTO")
public class CreatorShippingBackerList {
    //유저
    @Schema(description = "유저 ID", example = "1")
    private Long userId;
    @Schema(description = "유저 이메일", example = "email@example.com")
    private String email;
    @Schema(description = "유저 닉네임", example = "nickname")
    private String nickname;

    //창작자
    @Schema(description = "창작자 ID", example = "1")
    private Long creatorId;

    //리워드
    @Schema(description = "리워드 이름", example = "리워드 A")
    private String rewardName;

    //배송지
    @Schema(description = "수령인 이름", example = "홍길동")
    private String recipient;
    @Schema(description = "우편번호", example = "12345")
    private String postalCode;
    @Schema(description = "도로명 주소", example = "서울특별시 강남구 테헤란로 123")
    private String roadAddr;
    @Schema(description = "상세 주소", example = "101동 202호")
    private String detailAddr;
    @Schema(description = "수령인 전화번호", example = "010-1234-5678")
    private String recipientPhone;

    //배송
    @Schema(description = "배송 상태", example = "SHIPPED")
    private String shippingStatus;
    @Schema(description = "운송장 번호", example = "1234567890")
    private String trackingNum;
    @Schema(description = "배송 시작 일시", example = "2024-01-01T10:00:00")
    private LocalDateTime shippedAt;
    @Schema(description = "배송 완료 일시", example = "2024-01-05T15:30:00")
    private LocalDateTime deliveredAt;

    //후원상세
    @Schema(description = "후원 수량", example = "2")
    private Long quantity;

    // 프로젝트
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 기기 개발 프로젝트")
    private String title;

    @Schema(description = "후원 상세 ID", example = "1")
    private Long backingId;
    @Schema(description = "후원 생성 일시", example = "2024-01-01T09:00:00")
    private LocalDateTime createdAt;

}
