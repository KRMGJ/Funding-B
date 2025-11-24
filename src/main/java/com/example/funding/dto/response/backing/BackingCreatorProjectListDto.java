package com.example.funding.dto.response.backing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "BackingCreatorProjectListDto", description = "크리에이터가 본인의 후원한 프로젝트 목록을 조회할 때 사용하는 DTO")
public class BackingCreatorProjectListDto {
    //프로젝트
    @Schema(description = "크리에이터 아이디", example = "1")
    private Long creatorId;
    @Schema(description = "프로젝트 아이디", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "멋진 프로젝트")
    private String title;
    @Schema(description = "목표 금액", example = "1000000")
    private Long goalAmount;
    @Schema(description = "현재 금액", example = "500000")
    private Long currAmount;
    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "후원자 수", example = "150")
    private Long backerCnt;

    @Schema(description = "후원자 목록")
    List<BackingCreatorBackerList> backerList;

    //달성률
    @Schema(description = "달성률", example = "75.5")
    private double completionRate;
}
