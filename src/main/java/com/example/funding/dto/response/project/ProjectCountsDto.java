package com.example.funding.dto.response.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(name = "ProjectCountsDto", description = "프로젝트 카운트 응답 DTO")
public class ProjectCountsDto {
    @Schema(description = "전체 커뮤니티 수")
    private Section community;
    @Schema(description = "전체 리뷰 수")
    private Section review;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(name = "Section", description = "섹션별 카운트 정보")
    public static class Section {
        @Schema(description = "전체 카운트")
        private Long total;
    }
}
