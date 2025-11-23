package com.example.funding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "BackingDetail", description = "후원 상세 모델")
public class BackingDetail {
    @Schema(description = "후원 상세 ID", example = "1")
    private Long backingId;
    @Schema(description = "리워드 ID", example = "1")
    private Long rewardId;
    @Schema(description = "리워드 가격", example = "50000")
    private Long price;
    @Schema(description = "리워드 수량", example = "2")
    private Long quantity;
}

