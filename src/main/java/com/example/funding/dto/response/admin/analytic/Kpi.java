package com.example.funding.dto.response.admin.analytic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Kpi", description = "핵심 성과 지표(KPI) 데이터")
public class Kpi {
    @Schema(description = "총 사용자 수")
    private Long totalBackingAmount;
    @Schema(description = "총 수익(수수료)")
    private Long fee;
    @Schema(description = "성공률")
    private Double successRate;
    @Schema(description = "평균 후원 금액")
    private Long backingAmountAvg;
}
