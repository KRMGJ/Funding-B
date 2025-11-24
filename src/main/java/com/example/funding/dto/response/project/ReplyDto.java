package com.example.funding.dto.response.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "ReplyDto", description = "댓글 응답 DTO")
public class ReplyDto {
    @Schema(description = "댓글 ID", example = "1")
    private Long replyId;
    @Schema(description = "커뮤니티 ID", example = "1")
    private Long cmId;
    @Schema(description = "유저 ID", example = "1")
    private Long userId;
    @Schema(description = "댓글 내용", example = "이 프로젝트 정말 기대돼요!")
    private String content;
    @Schema(description = "비밀 댓글 여부", example = "N")
    private Character isSecret;
    @Schema(description = "댓글 작성일", example = "2023-10-05T14:48:00")
    private LocalDateTime createdAt;

    @Schema(description = "댓글 작성자 닉네임", example = "happyUser")
    private String nickname;
    @Schema(description = "댓글 작성자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImg;
}
