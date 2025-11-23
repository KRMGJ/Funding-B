package com.example.funding.dto.row;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "SettlementSummary", description = "정산 요약 정보")
public class SettlementSummary {
    @Schema(description = "대기 금액", example = "100000")
    private Long waitingAmount;
    @Schema(description = "완료 금액", example = "500000")
    private Long completedAmount;
    @Schema(description = "정산 건수", example = "5")
    private Long settledCount;
    @Schema(description = "은행명", example = "국민은행")
    private String bank;
    @Schema(description = "계좌번호", example = "123-456-7890")
    private String account;
}
