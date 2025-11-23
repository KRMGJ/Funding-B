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
@Schema(name = "로그인 사용자 정보 DTO", description = "로그인한 사용자의 상세 정보를 담고 있는 DTO")
public class LoginUserDto {
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    @Schema(description = "이메일", example = "email@example.com")
    private String email;
    @Schema(description = "닉네임", example = "nickname")
    private String nickname;
    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImg;
    @Schema(description = "가입 일자", example = "2023-01-01T12:00:00")
    private LocalDateTime joinedAt;
    @Schema(description = "팔로우 수", example = "100")
    private Integer followCnt;
    @Schema(description = "팔로잉 수", example = "150")
    private Character isCreator;
    @Schema(description = "크리에이터 ID", example = "1")
    private Long creatorId;
    @Schema(description = "사용자 역할", example = "USER")
    private String role;
}
