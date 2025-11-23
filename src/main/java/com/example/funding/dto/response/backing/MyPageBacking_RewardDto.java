package com.example.funding.dto.response.backing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "MyPageBacking_RewardDto", description = "마이페이지 후원 리워드 DTO")
public class MyPageBacking_RewardDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "리워드 ID", example = "10")
    private Long rewardId;
    @Schema(description = "리워드 이름", example = "스마트 워치 리워드")
    private String rewardName;
    @Schema(description = "리워드 가격", example = "150000")
    private Long price;
    @Schema(description = "리워드 배송일", example = "2025-01-15T10:00:00")
    private LocalDateTime deliveryDate;

    // 후원상세
    @Schema(description = "후원한 리워드 수량", example = "2")
    private Long quantity;
    @Schema(description = "후원 ID", example = "1001")
    private Long backingId;
    @Schema(description = "후원자 ID", example = "42")
    private Long userId;

}
