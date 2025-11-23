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
@Schema(name = "RpAddRequestDto", description = "신고 추가 요청 DTO")
public class RpAddRequestDto {
    @Schema(description = "신고자 ID", example = "1")
    private Long userId;
    @Schema(description = "신고 대상 ID", example = "2")
    private Long target;
    @Schema(description = "신고 사유", example = "부적절한 콘텐츠 게시")
    private String reason;
    @Schema(description = "신고 날짜", example = "2024-06-15T14:30:00")
    private LocalDateTime reportDate;
    @Schema(description = "신고 상태", example = "처리 중")
    private String reportStatus;
    @Schema(description = "신고 유형", example = "스팸")
    private String reportType;
}
