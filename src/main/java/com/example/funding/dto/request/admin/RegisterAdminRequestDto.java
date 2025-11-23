package com.example.funding.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "RegisterAdminRequestDto", description = "관리자 등록 요청 DTO")
public class RegisterAdminRequestDto {
    @NotBlank(message = "관리자 아이디는 필수입니다.")
    @Schema(description = "관리자 아이디", example = "admin123")
    private String adminId;
    @NotBlank(message = "관리자 비밀번호는 필수입니다.")
    @Schema(description = "관리자 비밀번호", example = "securePassword!")
    private String adminPwd;
}
