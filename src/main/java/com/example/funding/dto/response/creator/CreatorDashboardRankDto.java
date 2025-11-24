package com.example.funding.dto.response.creator;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "CreatorDashboardRankDto", description = "크리에이터 대시보드 랭킹 응답 DTO")
public class CreatorDashboardRankDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    //private Long creatorId;
    @Schema(description = "프로젝트 제목", example = "나의 첫 번째 프로젝트")
    private String title;

    //내 프로젝트 랭킹(후원자 수)
    @Schema(description = "후원자 수", example = "100")
    private Long backerCnt;
    //내 프로젝트 랭킹(좋아요 수)
    @Schema(description = "좋아요 수", example = "250")
    private Long likeCnt;
    //내 프로젝트 랭킹 (조회수)
    @Schema(description = "조회수", example = "1000")
    private Long viewCnt;

}
