package com.example.funding.dto.request.creator;

import com.example.funding.enums.CreatorType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(name = "CreatorRegisterRequestDto", description = "창작자 등록 요청 DTO")
public class CreatorRegisterRequestDto {
    @NotBlank(message = "창작자 이름은 필수입니다.")
    @Size(min = 2, max = 30, message = "창작자 이름은 2자 이상 30자 이하여야 합니다.")
    @Schema(description = "창작자 이름", example = "홍길동")
    private String creatorName;
    @NotNull(message = "창작자 타입은 필수입니다.")
    @Schema(description = "창작자 타입", example = "INDIVIDUAL")
    private CreatorType creatorType;
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    @Schema(description = "이메일", example = "email@example.com")
    private String email;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    @NotBlank(message = "전화번호는 필수입니다.")
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;
    @NotBlank(message = "은행 정보는 필수입니다.")
    @Schema(description = "은행 정보", example = "국민은행")
    private String bank;
    @NotBlank(message = "계좌 번호는 필수입니다.")
    @Schema(description = "계좌 번호", example = "123-456-78901234")
    private String account;
    @Nullable
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}$", message = "사업자 등록번호 형식이 올바르지 않습니다. (예: 123-45-67890)")
    @Schema(description = "사업자 등록번호", example = "123-45-67890")
    private String businessNumber;
    @Nullable
    @Schema(description = "프로필 이미지 파일")
    private MultipartFile profileImg;
    @Nullable
    @Size(max = 500, message = "소개 글은 최대 500자 이하여야 합니다.")
    @Schema(description = "소개 글", example = "안녕하세요, 저는 창작자 홍길동입니다.")
    private String bio;
}
