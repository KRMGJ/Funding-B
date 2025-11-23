package com.example.funding.dto.response.settlement;

import com.example.funding.dto.row.SettlementSummary;
import com.example.funding.model.Settlement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "CreatorSettlementDto", description = "크리에이터 정산 정보 DTO")
public class CreatorSettlementDto {
    @Schema(description = "크리에이터의 정산 내역 리스트")
    private List<Settlement> settlement;
    @Schema(description = "크리에이터의 정산 요약 정보")
    private SettlementSummary settlementSummary;
}
