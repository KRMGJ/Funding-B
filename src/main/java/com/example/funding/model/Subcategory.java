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
@Schema(name = "Subcategory", description = "세부 카테고리 모델")
public class Subcategory {
    @Schema(description = "세부 카테고리 ID", example = "1")
    private Long subctgrId;
    @Schema(description = "상위 카테고리 ID", example = "2")
    private Long ctgrId;
    @Schema(description = "세부 카테고리 이름", example = "스마트기기")
    private String subctgrName;
}
