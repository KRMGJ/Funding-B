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
@Schema(name = "NoticeUpdateRequestDto", description = "공지사항 수정 요청 DTO")
public class NoticeUpdateRequestDto {
    @Schema(description = "공지사항 ID", example = "1")
    private Long noticeId;
    @Schema(description = "관리자 ID", example = "10")
    private Long adId;
    @Schema(description = "공지사항 제목", example = "새로운 공지사항 제목")
    private String title;
    @Schema(description = "공지사항 내용", example = "새로운 공지사항 내용")
    private String content;
    @Schema(description = "공지사항 조회수", example = "100")
    private Long viewCnt;
    @Schema(description = "공지사항 생성일", example = "2024-06-15T10:15:30")
    private LocalDateTime createdAt;
}
