package com.example.funding.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CheckNicknameRequestDto", description = "닉네임 중복 확인 요청 DTO")
public class CheckNicknameRequestDto {
    @NotBlank(message = "닉네임은 필수입니다.")
    @Schema(description = "중복 확인할 닉네임", example = "coolUser123")
    private String nickname;
}
