package com.example.funding.dto.request.creator;

import com.example.funding.enums.CreatorType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(name = "Creator Update Request DTO", description = "크리에이터 정보 수정 요청을 위한 DTO")
public class CreatorUpdateRequestDto {
    @Schema(description = "크리에이터 고유 아이디", example = "1")
    private Long creatorId;
    @Schema(description = "크리에이터 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "크리에이터 유형", example = "INDIVIDUAL")
    private CreatorType creatorType;
    @Schema(description = "크리에이터 이메일", example = "email@example.com")
    private String email;
    @Schema(description = "크리에이터 전화번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "크리에이터 은행명", example = "국민은행")
    private String bank;
    @Schema(description = "크리에이터 계좌번호", example = "123-456-7890")
    private String account;
    @Schema(description = "크리에이터 사업자번호", example = "123-45-67890")
    private String businessNum;
    @Schema(description = "크리에이터 프로필 이미지 파일")
    private MultipartFile profileImg;
    @Schema(description = "크리에이터 프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImgUrl;
    @Schema(description = "크리에이터 소개글", example = "안녕하세요, 홍길동입니다.")
    private String bio;
}
