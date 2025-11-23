package com.example.funding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "Settlement", description = "정산 정보 모델")
public class Settlement {
    @Schema(description = "정산 ID", example = "1")
    private Long settlementId;
    @Schema(description = "프로젝트 ID", example = "10")
    private Long projectId;
    @Schema(description = "크리에이터 ID", example = "5")
    private Long creatorId;
    @Schema(description = "총 정산 금액", example = "100000")
    private Long totalAmount;
    @Schema(description = "수수료", example = "5000")
    private Long fee;
    @Schema(description = "정산 금액", example = "95000")
    private Long settlementAmount;
    @Schema(description = "정산 날짜", example = "2024-06-15T10:15:30")
    private LocalDateTime settlementDate;
    @Schema(description = "정산 상태", example = "COMPLETED")
    private String settlementStatus;
    @Schema(description = "환불 금액", example = "2000")
    private Long refundAmount;
}
