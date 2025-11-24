package com.example.funding.dto.request.shipping;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "ShippingStatusDto", description = "배송 상태 업데이트를 위한 DTO")
public class ShippingStatusDto {
    @Schema(description = "후원 ID", example = "12345")
    private Long backingId;
    @Schema(description = "배송 상태", example = "SHIPPED", allowableValues = {"PENDING", "SHIPPED", "DELIVERED"})
    private String shippingStatus;
    @Schema(description = "운송장 번호", example = "1Z999AA10123456784")
    private String trackingNum;
    @Schema(description = "배송 시작 시간", example = "2024-06-01T10:15:30")
    private LocalDateTime shippedAt;
    @Schema(description = "배송 완료 시간", example = "2024-06-05T15:30:00")
    private LocalDateTime deliveredAt;
    @Schema(description = "기존 운송장 번호 (운송장 변경 시 필요)", example = "1Z999AA10123456784")
    private String originalTrackingNum;
}
