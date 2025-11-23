package com.example.funding.model;

import com.example.funding.enums.Role;
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
@Schema(name = "User", description = "사용자 정보 모델")
public class User {
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    @Schema(description = "이메일", example = "email@example.com")
    private String email;
    @Schema(description = "비밀번호", example = "securePassword123")
    private String pwd;
    @Schema(description = "닉네임", example = "nickname")
    private String nickname;
    @Schema(description = "프로필 이미지 URL", example = "http://example.com/profile.jpg")
    private String profileImg;
    @Schema(description = "가입 일시", example = "2023-01-01T12:00:00")
    private LocalDateTime joinedAt;
    @Schema(description = "마지막 로그인 일시", example = "2023-01-15T08:30:00")
    private LocalDateTime lastLoginAt;
    @Schema(description = "팔로우 수", example = "100")
    private Integer followCnt;
    @Schema(description = "정지 여부", example = "N")
    private Character isSuspended;
    @Schema(description = "정지 사유", example = "Inappropriate behavior")
    private String reason;
    @Schema(description = "정지 일시", example = "2023-02-01T10:00:00")
    private LocalDateTime suspendedAt;
    @Schema(description = "해제 일시", example = "2023-03-01T10:00:00")
    private LocalDateTime releasedAt;
    @Schema(description = "크리에이터 여부", example = "Y")
    private Character isCreator;
    @Schema(description = "사용자 역할", example = "USER")
    private Role role;
}
