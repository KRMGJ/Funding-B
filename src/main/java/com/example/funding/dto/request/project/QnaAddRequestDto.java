package com.example.funding.dto.request.project;

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
@Schema(name = "QnaAddRequestDto", description = "qna 추가 요청 DTO")
public class QnaAddRequestDto {
    @Schema(description = "프로젝트 아이디", example = "1")
    private Long projectId;
    @Schema(description = "유저 아이디", example = "1")
    private Long userId;
    @Schema(description = "qna 내용", example = "qna 내용입니다.")
    private String content;
    @Schema(description = "생성일", example = "2024-06-01T12:00:00")
    private LocalDateTime createdAt;
}
