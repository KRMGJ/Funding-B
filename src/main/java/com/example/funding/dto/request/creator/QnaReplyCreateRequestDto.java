package com.example.funding.dto.request.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "QnaReplyCreateRequestDto", description = "Q&A 답변 생성 요청 DTO")
public class QnaReplyCreateRequestDto {
    @Schema(description = "답변 내용", example = "이것은 답변 내용입니다.")
    private String content;
    @Schema(description = "크리에이터 ID", example = "123")
    private Long creatorId;
}
