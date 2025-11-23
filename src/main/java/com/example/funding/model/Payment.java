package com.example.funding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(name = "Payment", description = "결제 정보 모델")
public class Payment {
    @Schema(description = "결제 ID", example = "1")
    private Long paymentId;
    @Schema(description = "후원 ID", example = "1")
    private Long backingId;
    @Schema(description = "주문 ID", example = "ORD123456789")
    private String orderId;
    @Schema(description = "결제 수단", example = "CARD")
    private String method;
    @Schema(description = "결제 상태", example = "COMPLETED")
    private String status;
    @Schema(description = "결제 금액", example = "50000")
    private Long amount;
    @Schema(description = "카드 회사", example = "VISA")
    private String cardCompany;
    @Schema(description = "결제 시각", example = "2024-06-01T12:00:00")
    private LocalDateTime createdAt;
}
