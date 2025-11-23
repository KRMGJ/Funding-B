package com.example.funding.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "SignUpRequestDto", description = "회원가입 요청 DTO")
public class SignUpRequestDto {
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    @Schema(description = "사용자 이메일", example = "email@example.com")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    @Schema(description = "사용자 비밀번호", example = "P@ssw0rd!")
    private String password;
    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하여야 합니다.")
    @Schema(description = "사용자 닉네임", example = "nickname")
    private String nickname;
}
