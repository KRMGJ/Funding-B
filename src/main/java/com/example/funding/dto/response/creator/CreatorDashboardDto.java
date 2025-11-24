package com.example.funding.dto.response.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "CreatorDashboardDto", description = "크리에이터 대시보드 응답 DTO")
public class CreatorDashboardDto {
    @Schema(description = "크리에이터 ID", example = "1")
    private Long creatorId;

    @Schema(description = "프로젝트 총 개수", example = "10")
    private Integer projectTotal;
    @Schema(description = "총 모인 금액", example = "5000000")
    private Long totalAmount;
    @Schema(description = "총 후원자 수", example = "150")
    private Long totalBackingCnt;
    @Schema(description = "승인 대기 중인 프로젝트 수", example = "2")
    private Long totalVerifyingCnt;

    //내프로젝트 성공률 (파이차트 계산용)
    @Schema(description = "전체 프로젝트 개수", example = "10")
    private Double totalProjectCnt; // 전체 프로젝트 개수
    @Schema(description = "실패한 프로젝트 개수", example = "3")
    private Double projectFailedCnt; // 실패한 프로젝트 개수
    @Schema(description = "성공한 프로젝트 개수", example = "7")
    private Double projectSuccessCnt; // 성공한 프로젝트 개수
    @Schema(description = "실패한 프로젝트 퍼센트", example = "30.0")
    private Double projectFailedPercentage; // 실패한 프로젝트 퍼센트
    @Schema(description = "성공한 프로젝트 퍼센트", example = "70.0")
    private Double projectSuccessPercentage; // 성공한 프로젝트 퍼센트

    @Schema(description = "상위 3개 프로젝트 - 후원자 수 기준")
    private List<CreatorDashboardRankDto> top3BackerCnt;
    @Schema(description = "상위 3개 프로젝트 - 좋아요 수 기준")
    private List<CreatorDashboardRankDto> top3LikeCnt;
    @Schema(description = "상위 3개 프로젝트 - 조회수 기준")
    private List<CreatorDashboardRankDto> top3ViewCnt;

    @Schema(description = "일별 후원 현황")
    private List<DailyCountDto> dailyStatus;
    @Schema(description = "월별 후원 현황")
    private List<MonthCountDto> monthStatus;
}
