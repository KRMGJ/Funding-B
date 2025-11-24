package com.example.funding.dto.response.shipping;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "CreatorShippingProjectList", description = "크리에이터 배송 프로젝트 리스트 응답 DTO")
public class CreatorShippingProjectList {
    @Schema(description = "크리에이터 ID", example = "1")
    private Long creatorId;
    @Schema(description = "프로젝트 ID", example = "10")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치")
    private String title;
    @Schema(description = "후원자 수", example = "150")
    private Long backerCnt;
    @Schema(description = "배송 완료된 후원 수", example = "120")
    private Long completedShippingCnt;
}
