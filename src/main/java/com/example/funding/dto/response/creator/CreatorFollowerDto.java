package com.example.funding.dto.response.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "CreatorFollowerDto", description = "크리에이터 팔로워 정보 DTO")
public class CreatorFollowerDto {
    @Schema(description = "팔로워 유저 ID", example = "123")
    private Long userId;
    @Schema(description = "팔로워 닉네임", example = "john_doe")
    private String nickname;
    @Schema(description = "팔로워 프로필 이미지 URL", example = "http://example.com/profile.jpg")
    private String userProfileImg;
    @Schema(description = "팔로우 날짜", example = "2024-01-01")
    private LocalDate followDate;
    @Schema(description = "팔로워가 크리에이터인지 여부", example = "true")
    private boolean isCreator;

    // 팔로워가 크리에이터인 경우만 값 존재
    @Schema(description = "크리에이터 ID", example = "456")
    private Long creatorId;
    @Schema(description = "크리에이터 이름", example = "Jane Smith")
    private String creatorName;
    @Schema(description = "크리에이터 프로필 이미지 URL", example = "https://example.com/creator_profile.jpg")
    private String creatorProfileImg;

    @Schema(description = "팔로우 가능 여부", example = "true")
    private boolean canFollow;
    @Schema(description = "현재 사용자가 해당 팔로워를 팔로잉 중인지 여부", example = "false")
    private boolean isFollowing;
}
