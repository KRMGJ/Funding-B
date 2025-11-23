package com.example.funding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "공통 응답 DTO")
public class ResponseDto<T> {
    @Schema(description = "응답 코드", example = "200")
    private int code;
    @Schema(description = "응답 메시지", example = "성공")
    private String message;
    @Schema(description = "응답 데이터")
    private T data;

    /**
     * <p>성공 응답 생성 메서드</p>
     * @param code    응답 코드
     * @param message 응답 메시지
     * @param data    응답 데이터
     * @param <T>     데이터 타입
     * @return 성공 응답 DTO
     * @since 2025-08-27
     * @author 장민규
     */
    public static <T> ResponseDto<T> success(int code, String message, T data) {
        return new ResponseDto<>(code, message, data);
    }

    /**
     * <p>실패 응답 생성 메서드</p>
     * @param code    응답 코드
     * @param message 응답 메시지
     * @param <T>     데이터 타입
     * @return 실패 응답 DTO
     * @since 2025-08-27
     * @author 장민규
     */
    public static <T> ResponseDto<T> fail(int code, String message) {
        return new ResponseDto<>(code, message, null);
    }
}
