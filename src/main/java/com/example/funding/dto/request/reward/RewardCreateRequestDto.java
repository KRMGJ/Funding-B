package com.example.funding.dto.request.reward;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "RewardCreateRequestDto", description = "리워드 생성 요청 DTO")
public class RewardCreateRequestDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "리워드 이름", example = "스페셜 에디션 티셔츠")
    private String rewardName;
    @Schema(description = "리워드 가격", example = "50000")
    private Long price;
    @Schema(description = "리워드 내용", example = "한정판 스페셜 에디션 티셔츠와 감사 카드 포함")
    private String rewardContent;
    @Schema(description = "리워드 배송 예정일", example = "2024-09-01T00:00:00")
    private LocalDateTime deliveryDate;
    @Schema(description = "리워드 수량", example = "100")
    private Integer rewardCnt;
    @Schema(description = "리워드 배송 여부", example = "Y")
    private Character isPosting;
}
