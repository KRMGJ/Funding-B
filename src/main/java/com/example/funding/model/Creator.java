package com.example.funding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Creator", description = "Creator 모델")
public class Creator {
    @Schema(description = "크리에이터 ID", example = "1")
    private Long creatorId;
    @Schema(description = "유저 ID", example = "42")
    private Long userId;
    @Schema(description = "크리에이터 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImg;
    @Schema(description = "크리에이터 소개", example = "안녕하세요, 저는 크리에이터 홍길동입니다.")
    private String bio;
    @Schema(description = "이메일 주소", example = "email@example.com")
    private String email;
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "은행 이름", example = "국민은행")
    private String bank;
    @Schema(description = "계좌 번호", example = "123-456-7890")
    private String account;
    @Schema(description = "사업자 번호", example = "123-45-67890")
    private String businessNum;
    @Schema(description = "팔로워 수", example = "1000")
    private Long followerCnt;
    @Schema(description = "크리에이터 유형", example = "개인")
    private String creatorType;
}
