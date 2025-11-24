package com.example.funding.dto.response.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "CommunityDto", description = "커뮤니티 응답 DTO")
public class CommunityDto {
    @Schema(description = "커뮤니티 ID", example = "1")
    private Long cmId;
    @Schema(description = "커뮤니티 내용", example = "이 프로젝트에 대해 어떻게 생각하시나요?")
    private String cmContent;
    @Schema(description = "커뮤니티 생성일", example = "2023-10-01T12:34:56")
    private LocalDateTime createdAt;
    @Schema(description = "테이블 코드", example = "PROJECT")
    private String code;
    @Schema(description = "작성자 닉네임", example = "user123")
    private String nickname;
    @Schema(description = "작성자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImg;
    @Schema(description = "댓글 수", example = "5")
    private Long replyCnt;
}
