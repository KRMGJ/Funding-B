package com.example.funding.model;

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
@Schema(name = "News", description = "프로젝트 새소식")
public class News {
    @Schema(description = "새소식 ID", example = "1")
    private Long newsId;
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "새소식 내용", example = "프로젝트가 성공적으로 시작되었습니다!")
    private String content;
    @Schema(description = "생성 날짜", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
}
