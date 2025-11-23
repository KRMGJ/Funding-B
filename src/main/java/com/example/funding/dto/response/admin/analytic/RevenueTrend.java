package com.example.funding.dto.response.admin.analytic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "RevenueTrend", description = "수익 추세 데이터 DTO")
public class RevenueTrend {
    @Schema(description = "월")
    private String month;
    @Schema(description = "프로젝트 수")
    private Integer projectCnt;
    @Schema(description = "수익")
    private Long revenue;
}
