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
@Schema(name = "TotalCountsDto", description = "총 리뷰 수, 총 프로젝트 수, 총 팔로워 수를 나타내는 DTO")
public class TotalCountsDto {
    @Schema(description = "총 리뷰 수", example = "150")
    private Long totalReviews;
    @Schema(description = "총 프로젝트 수", example = "25")
    private Integer totalProjects;
    @Schema(description = "총 팔로워 수", example = "3000")
    private Integer totalFollowers;
}
