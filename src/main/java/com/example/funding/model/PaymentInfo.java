package com.example.funding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(name = "PaymentInfo", description = "결제 정보 모델")
public class PaymentInfo {
    @Schema(description = "결제 정보 ID", example = "1")
    private Long payInfoId;
    @Schema(description = "유저 ID", example = "1")
    private Long userId;
    @Schema(description = "카드 회사", example = "VISA")
    private String cardCompany;
    @Schema(description = "결제 방법", example = "신용카드")
    private String method;
    @Schema(description = "카드 번호", example = "1234-5678-9012-3456")
    private String cardNum;
}
