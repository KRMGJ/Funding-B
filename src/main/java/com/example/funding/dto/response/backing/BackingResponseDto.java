package com.example.funding.dto.response.backing;

import com.example.funding.dto.response.address.AddressResponseDto;
import com.example.funding.dto.response.payment.BackingPagePaymentDto;
import com.example.funding.model.PaymentInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "BackingResponseDto", description = "후원 준비 응답 DTO")
public class BackingResponseDto {
    // 유저 테이블
    @Schema(description = "유저 ID", example = "1")
    private Long userId;
    @Schema(description = "유저 닉네임", example = "john_doe")
    private String nickname;
    @Schema(description = "유저 이메일", example = "email@example.com")
    private String email;

    // 배송지 테이블
    // 배송지 목록이 0개일 수 있음
    @Schema(description = "유저 배송지 목록")
    private List<AddressResponseDto> addressList;

    //창작자 테이블
    @Schema(description = "창작자 ID", example = "10")
    private Long creatorId;
    @Schema(description = "창작자 이름", example = "Jane Smith")
    private String creatorName;
    @Schema(description = "창작자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImg;

    //프로젝트 테이블
    @Schema(description = "프로젝트 ID", example = "100")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "Innovative Gadget")
    private String title;
    @Schema(description = "프로젝트 썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "프로젝트 현재 모금액", example = "50000")
    private Integer currAmount;
    @Schema(description = "프로젝트 목표 모금액", example = "100000")
    private Integer goalAmount;

    @Schema(description = "후원 리워드 목록")
    private List<BackingRewardDto> rewardsList;

    @Schema(description = "후원 페이지 결제 수단 목록")
    private List<PaymentInfo> backingPagePaymentList;
}
