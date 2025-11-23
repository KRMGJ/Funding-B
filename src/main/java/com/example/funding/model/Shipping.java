package com.example.funding.model;

import com.example.funding.enums.BackingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(name = "Shipping", description = "배송 정보 모델")
public class Shipping {
    @Schema(description = "배송 ID", example = "1")
    private Long shippingId;
    @Schema(description = "후원 ID", example = "1")
    private Long backingId;
    @Schema(description = "주소 ID", example = "1")
    private Long addrId;
    @Schema(description = "배송 상태", example = "SHIPPED")
    private BackingStatus shippingStatus;
    @Schema(description = "운송장 번호", example = "1234567890")
    private String trackingNum;
    @Schema(description = "배송 시작 시간", example = "2024-01-01T10:00:00")
    private LocalDateTime shippedAt;
    @Schema(description = "배송 완료 시간", example = "2024-01-05T15:30:00")
    private LocalDateTime deliveredAt;
}
