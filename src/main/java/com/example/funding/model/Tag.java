package com.example.funding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "Tag", description = "프로젝트 태그 모델")
public class Tag {
    @Schema(description = "태그 ID", example = "1")
    private Long tagId;
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "태그 이름", example = "혁신")
    private String tagName;
}
