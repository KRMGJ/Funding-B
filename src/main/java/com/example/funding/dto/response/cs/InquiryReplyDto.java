package com.example.funding.dto.response.cs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "InquiryReplyDto", description = "1:1 문의 답변 정보 DTO")
public class InquiryReplyDto {
    @Schema(description = "답변 ID", example = "1")
    private Long replyId;
    @Schema(description = "문의 ID", example = "1")
    private Long inqId;
    @Schema(description = "관리자 ID", example = "admin123")
    private Long adId;
    @Schema(description = "답변 내용", example = "문의에 대한 답변 내용입니다.")
    private String content;
    @Schema(description = "답변 작성 일시", example = "2024-06-01T12:34:56")
    private LocalDateTime createdAt;
}
