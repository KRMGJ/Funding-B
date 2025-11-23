package com.example.funding.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CreateCategoryDto", description = "카테고리 생성 요청 DTO")
public class CreateCategoryDto {
    @Schema(description = "카테고리 이름", example = "기부금품")
    private String ctgrName;
}
