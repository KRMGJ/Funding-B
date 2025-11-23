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
@Schema(name = "Category", description = "카테고리 모델")
public class Category {
    @Schema(description = "카테고리 ID", example = "1")
    private Long ctgrId;
    @Schema(description = "카테고리 이름", example = "기부")
    private String ctgrName;
}
