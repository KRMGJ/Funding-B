package com.example.funding.dto.request.reward;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "RewardBackingRequestDto", description = "후원 리워드 요청 DTO")
public class RewardBackingRequestDto {
    // 리워드
    @Schema(description = "리워드 ID", example = "1")
    private Long rewardId;
    @Schema(description = "리워드 이름", example = "스페셜 에디션 티셔츠")
    private String rewardName;
    @Schema(description = "리워드 가격", example = "50000")
    private Long price;
    @Schema(description = "리워드 내용", example = "한정판 티셔츠와 감사 카드 포함")
    private String rewardContent;
    @Schema(description = "리워드 수량", example = "2")
    private Long quantity;

}
