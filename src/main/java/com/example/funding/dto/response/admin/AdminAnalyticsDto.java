package com.example.funding.dto.response.admin;

import com.example.funding.dto.response.admin.analytic.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "AdminAnalyticsDto", description = "관리자 대시보드 분석 데이터 DTO")
public class AdminAnalyticsDto {
    @Schema(description = "핵심 성과 지표(KPI)")
    private Kpi kpi;
    @Schema(description = "수익 추세 데이터 목록")
    private List<RevenueTrend> revenueTrends;
    @Schema(description = "사용자 가입 추세 데이터 목록")
    private List<RewardSalesTop> rewardSalesTops;
    @Schema(description = "리워드 판매 상위 데이터 목록")
    private List<PaymentMethod> paymentMethods;
    @Schema(description = "카테고리별 성공률 데이터 목록")
    private List<CategorySuccess> categorySuccesses;
}
