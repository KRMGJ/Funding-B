package com.example.funding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "Address", description = "배송지 정보 모델")
public class Address {
    @Schema(description = "배송지 ID", example = "1")
    private Long addrId;
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    @Schema(description = "배송지 이름", example = "집")
    private String addrName;
    @Schema(description = "수령인 이름", example = "홍길동")
    private String recipient;
    @Schema(description = "수령인 전화번호", example = "010-1234-5678")
    private String recipientPhone;
    @Schema(description = "우편번호", example = "12345")
    private String postalCode;
    @Schema(description = "도로명 주소", example = "서울특별시 강남구 테헤란로 123")
    private String roadAddr;
    @Schema(description = "상세 주소", example = "101동 202호")
    private String detailAddr;
    @Schema(description = "기본 배송지 여부 (Y/N)", example = "Y")
    private Character isDefault;
}

