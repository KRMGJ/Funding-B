package com.example.funding.dto.request.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "NewsCreateRequestDto", description = "새소식 생성 요청 DTO")
public class NewsCreateRequestDto {
    @Schema(description = "새소식 내용", example = "새로운 소식이 업데이트 되었습니다!")
    private String content;
}
