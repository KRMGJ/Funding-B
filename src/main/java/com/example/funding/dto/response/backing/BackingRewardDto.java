package com.example.funding.dto.response.backing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(name = "BackingRewardDto", description = "후원 리워드 정보 DTO")
public class BackingRewardDto {
    @Schema(description = "후원 리워드 ID", example = "1000")
    private Long rewardId;
    @Schema(description = "후원 리워드 이름", example = "Early Bird Special")
    private String rewardName;
    @Schema(description = "후원 리워드 금액", example = "5000")
    private Long price;
    @Schema(description = "후원 리워드 수량", example = "100")
    private Long quantity;
    @Schema(description = "후원 리워드 배송 예정일", example = "2024-12-31T23:59:59")
    private LocalDateTime deliveryDate;
}
