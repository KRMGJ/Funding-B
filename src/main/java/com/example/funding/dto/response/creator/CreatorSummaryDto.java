package com.example.funding.dto.response.creator;

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
@Schema(name = "CreatorSummaryDto", description = "크리에이터 요약 정보 응답 DTO")
public class CreatorSummaryDto {
    @Schema(description = "크리에이터 정보")
    private CreatorRow creator;
    @Schema(description = "크리에이터 통계 정보")
    private Stats stats;
    @Schema(description = "팔로우 여부")
    private Boolean isFollowed;
    @Schema(description = "팔로워 수", example = "1500")
    private Long followerCount;
    @Schema(description = "마지막 로그인 시간", example = "2024-06-15T14:30:00")
    private LocalDateTime lastLogin;

    @Getter
    @Setter
    @Schema(name = "CreatorRow", description = "크리에이터 기본 정보")
    public static class CreatorRow {
        @Schema(description = "크리에이터 ID", example = "42")
        private Long creatorId;
        @Schema(description = "크리에이터 이름", example = "홍길동")
        private String creatorName;
        @Schema(description = "크리에이터 프로필 이미지 URL", example = "https://example.com/profile.jpg")
        private String profileImg;
    }

    @Getter
    @Setter
    @Schema(name = "Stats", description = "크리에이터 통계 정보")
    public static class Stats {
        @Schema(description = "프로젝트 수", example = "25")
        private Long projectCount;
        @Schema(description = "총 후원자 수", example = "5000")
        private Long totalBackers;
        @Schema(description = "총 후원 금액", example = "12500000")
        private Long totalAmount;
    }
}
