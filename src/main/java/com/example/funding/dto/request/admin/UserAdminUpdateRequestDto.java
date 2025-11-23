package com.example.funding.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(name = "UserAdminUpdateRequestDto", description = "관리자 회원 정보 수정 요청 DTO")
public class UserAdminUpdateRequestDto {
    @Schema(description = "회원 ID", example = "1")
    private Long userId;
    @Schema(description = "닉네임", example = "new_nickname")
    private String nickname;
    @Schema(description = "정지 여부", example = "Y or N")
    private Character isSuspended;
    @Schema(description = "정지 사유", example = "규정 위반")
    private String reason;
    @Schema(description = "정지 일시", example = "2024-01-01T12:00:00")
    private LocalDateTime suspendedAt;
    @Schema(description = "해제 일시", example = "2024-01-15T12:00:00")
    private LocalDateTime releasedAt;
}
