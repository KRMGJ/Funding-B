package com.example.funding.dto.response.cs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "QnaReplyDto", description = "Q&A 답변 정보를 담고 있는 DTO")
public class QnaReplyDto {
    @Schema(description = "Q&A 답변 ID", example = "1")
    private Long replyId;
    @Schema(description = "Q&A ID", example = "10")
    private Long qnaId;
    @Schema(description = "답변 내용", example = "이것은 답변 내용입니다.")
    private String content;
    @Schema(description = "답변 생성일시", example = "2023-10-01T12:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "크리에이터 ID", example = "100")
    private Long creatorId;
}
