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
@Schema(name = "NoticeAddRequestDto", description = "공지사항 추가 요청 DTO")
public class NoticeAddRequestDto {
    @Schema(description = "관리자 ID", example = "1")
    private Long adId;
    @Schema(description = "공지사항 제목", example = "새로운 공지사항 제목")
    private String title;
    @Schema(description = "공지사항 내용", example = "새로운 공지사항 내용")
    private String content;
    @Schema(description = "공지사항 조회수", example = "0")
    private Long viewCnt;
    @Schema(description = "공지사항 생성일", example = "2024-06-01T12:00:00")
    private LocalDateTime createdAt;
}
