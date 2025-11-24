package com.example.funding.dto.request.cs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "IqrReplyCreateRequestDto", description = "1:1 문의 답변 생성 요청 DTO")
public class IqrReplyCreateRequestDto {
    @Schema(description = "답변 내용", example = "문의해 주셔서 감사합니다. 고객님의 문의에 대한 답변입니다.")
    private String content;
}
