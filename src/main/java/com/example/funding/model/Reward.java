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
@Schema(name = "Reward", description = "리워드 모델")
public class Reward {
    @Schema(description = "리워드 ID", example = "1")
    private Long rewardId;
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "리워드 이름", example = "스마트 워치 기본형")
    private String rewardName;
    @Schema(description = "리워드 가격", example = "150000")
    private Long price;
    @Schema(description = "리워드 내용", example = "최신 스마트 워치 제공")
    private String rewardContent;
    @Schema(description = "리워드 개수", example = "100")
    private Integer rewardCnt;
    @Schema(description = "남은 리워드 개수", example = "75")
    private Integer remain;
    @Schema(description = "배송 여부 (Y/N)", example = "Y")
    private Character isPosting;
    @Schema(description = "배송 예정일", example = "2024-09-01T00:00:00")
    private LocalDateTime deliveryDate;
    @Schema(description = "리워드 생성 일시", example = "2024-06-20T12:00:00")
    private LocalDateTime createdAt;
}
