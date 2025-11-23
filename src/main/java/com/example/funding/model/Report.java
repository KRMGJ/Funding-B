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
@Schema(name = "Report", description = "신고 모델")
public class Report {
    @Schema(description = "신고 ID", example = "1")
    private Long reportId;
    @Schema(description = "신고자 사용자 ID", example = "42")
    private Long userId;
    @Schema(description = "신고 유형")
    private String reportType;
    @Schema(description = "신고 대상 ID", example = "1001")
    private Long target;
    @Schema(description = "신고 사유", example = "부적절한 콘텐츠")
    private String reason;
    @Schema(description = "신고 날짜", example = "2024-06-15T14:30:00")
    private LocalDateTime reportDate;
    @Schema(description = "신고 상태", example = "처리중")
    private String reportStatus;
}
