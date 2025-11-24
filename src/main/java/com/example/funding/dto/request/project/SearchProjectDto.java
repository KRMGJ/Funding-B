package com.example.funding.dto.request.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "SearchProjectDto", description = "프로젝트 검색 요청 DTO")
public class SearchProjectDto {
    @Schema(description = "검색 키워드", example = "환경")
    private String keyword;
    @Schema(description = "카테고리 ID", example = "1")
    private Long ctgrId;
    @Schema(description = "서브카테고리 ID", example = "10")
    private Long subctgrId;
    @Schema(description = "정렬 기준 (view: 조회수, recent: 최신순, popular: 인기순, endDate: 종료임박순)", example = "view")
    private String sort = "view";
    //종료 전만 보기
    @Schema(description = "활성화된 프로젝트만 조회 여부", example = "true")
    private Boolean activeOnly = true;
}
