package com.example.funding.dto.response.settlement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "SettlementItem", description = "정산 내역 아이템")
public class SettlementItem {
    @Schema(description = "정산 ID", example = "1")
    private Long settlementId;
    @Schema(description = "프로젝트 ID", example = "10")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치 개발")
    private String projectTitle;
    @Schema(description = "창작자 ID", example = "100")
    private Long creatorId;
    @Schema(description = "창작자 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "총 정산 금액", example = "500000")
    private Long totalAmount;
    @Schema(description = "수수료", example = "50000")
    private Long fee;
    @Schema(description = "정산 금액", example = "450000")
    private Long settlementAmount;
    @Schema(description = "환불 금액", example = "0")
    private Long refundAmount;
    @Schema(description = "정산 일시", example = "2024-06-15T14:30:00")
    private LocalDateTime settlementDate;
    @Schema(description = "정산 상태", example = "COMPLETED")
    private String settlementStatus;
}
