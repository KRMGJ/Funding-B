package com.example.funding.dto.request.cs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(name = "IqrAddRequestDto", description = "1:1 문의 추가 요청 DTO")
public class IqrAddRequestDto {
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    @Schema(description = "관리자 ID", example = "10")
    private Long adId;
    @Schema(description = "문의 제목", example = "배송 문의")
    private String title;
    @Schema(description = "문의 내용", example = "제 주문이 언제 배송되나요?")
    private String content;
    @Schema(description = "문의 생성 일시", example = "2024-06-15T10:15:30")
    private LocalDateTime createdAt;
    @Schema(description = "문의 취소 여부", example = "N")
    private Character isCanceled;
    @Schema(description = "문의 카테고리", example = "배송")
    private String ctgr;
    @Schema(description = "답변 여부", example = "N")
    private Character isAnswer;
}
