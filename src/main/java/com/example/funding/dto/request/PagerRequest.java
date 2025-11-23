package com.example.funding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "PagerRequest", description = "페이징 요청 정보")
public class PagerRequest {
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    @Schema(description = "페이지 번호", example = "1", minimum = "1")
    private Integer page;
    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
    @Schema(description = "페이지 크기", example = "10", minimum = "1")
    private Integer size;
    @Min(value = 1, message = "그룹당 페이지 수는 1 이상이어야 합니다.")
    @Schema(description = "그룹당 페이지 수", example = "5", minimum = "1")
    private Integer perGroup;
}
