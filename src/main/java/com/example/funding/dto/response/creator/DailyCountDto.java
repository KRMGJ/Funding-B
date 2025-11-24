package com.example.funding.dto.response.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(name = "DailyCountDto", description = "일별 카운트 DTO")
public class DailyCountDto {
    @Schema(description = "날짜", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "카운트", example = "5")
    private Long count;
}
