package com.example.funding.dto.request.payment;

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
@Schema(name = "AddCardDto", description = "결제 수단 추가 요청 DTO")
public class AddCardDto {
    @Schema(description = "결제 정보 ID", example = "1")
    private Long payInfoId;
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    @Schema(description = "카드사", example = "Visa")
    private String cardCompany;
    @Schema(description = "결제 수단", example = "Credit Card")
    private String method;
    @Schema(description = "카드 번호", example = "1234-5678-9012-3456")
    private String cardNum;
}
