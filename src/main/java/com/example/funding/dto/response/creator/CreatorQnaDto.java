package com.example.funding.dto.response.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "CreatorQnaDto", description = "Creator Q&A 응답 DTO")
public class CreatorQnaDto {
    @Schema(description = "Q&A ID", example = "1")
    private Long qnaId;
    @Schema(description = "프로젝트 ID", example = "10")
    private Long projectId;
    @Schema(description = "유저 ID", example = "100")
    private Long userId;
    @Schema(description = "크리에이터 ID", example = "200")
    private Long creatorId;
    @Schema(description = "Q&A 내용", example = "이 프로젝트에 대해 궁금한 점이 있습니다.")
    private String content;
    @Schema(description = "Q&A 생성일", example = "2024-06-15T10:15:30")
    private LocalDateTime createdAt;
    @Schema(description = "Q&A 제목", example = "프로젝트 관련 질문입니다.")
    private String title;
}