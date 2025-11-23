package com.example.funding.dto.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "UserSummaryDto", description = "사용자 요약 정보 DTO")
public class UserSummaryDto {
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    @Schema(description = "후원 수", example = "150")
    private Long backingCount;
    @Schema(description = "좋아요 수", example = "300")
    private Long likedCount;
    @Schema(description = "크리에이터 팔로우 수", example = "75")
    private Long followCreatorCount;
    @Schema(description = "알림 수", example = "20")
    private Long notificationCount;
}
