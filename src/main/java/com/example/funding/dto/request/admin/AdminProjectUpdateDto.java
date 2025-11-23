package com.example.funding.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "AdminProjectUpdateDto", description = "관리자 프로젝트 수정 DTO")
public class AdminProjectUpdateDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "서브 카테고리 ID", example = "2")
    private Long subctgrId;
    @Schema(description = "프로젝트 제목", example = "새로운 프로젝트 제목")
    private String title;
    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "프로젝트 상태", example = "APPROVED")
    private String projectStatus;
}
