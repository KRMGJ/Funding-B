package com.example.funding.dto.request.cs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "ReportUpdateRequestDto", description = "신고 업데이트 요청 DTO")
public class ReportUpdateRequestDto {
    @Schema(description = "신고 ID", example = "1")
    private Long reportId;
    @Schema(description = "신고 사유", example = "부적절한 콘텐츠")
    private String reason;
    @Schema(description = "신고 상태", example = "처리중")
    private String reportStatus;
}
