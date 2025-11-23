package com.example.funding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Backing", description = "후원 모델")
public class Backing {
    @Schema(description = "후원 ID", example = "1")
    private Long backingId;
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    @Schema(description = "결제 정보 ID", example = "1")
    private Long payInfoId;
    @Schema(description = "후원 총 금액", example = "50000")
    private Long amount;
    @Schema(description = "후원 생성 일시", example = "2024-01-01T12:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "후원 상태", example = "PENDING")
    private String backingStatus;
}
