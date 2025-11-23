package com.example.funding.dto.response.admin.analytic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CategorySuccess", description = "카테고리별 성공률 데이터 DTO")
public class CategorySuccess {
    @Schema(description = "카테고리 이름")
    private String categoryName;
    @Schema(description = "성공 건수")
    private Long successCnt;
    @Schema(description = "실패 건수")
    private Long failCnt;
}
