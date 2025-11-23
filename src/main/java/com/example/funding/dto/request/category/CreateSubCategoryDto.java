package com.example.funding.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CreateSubCategoryDto", description = "하위 카테고리 생성 요청 DTO")
public class CreateSubCategoryDto {
    @Schema(description = "상위 카테고리 ID", example = "1")
    private Long ctgrId;
    @Schema(description = "하위 카테고리 이름", example = "스마트기기")
    private String subctgrName;
}
