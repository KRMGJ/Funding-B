package com.example.funding.dto.response.admin.analytic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "RewardSalesTop", description = "리워드 판매 상위 데이터 DTO")
public class RewardSalesTop {
    @Schema(description = "리워드 이름")
    private String rewardName;
    @Schema(description = "판매 수량")
    private Long qty;
    @Schema(description = "수익 금액")
    private Long revenue;
}
