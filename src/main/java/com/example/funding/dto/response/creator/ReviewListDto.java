package com.example.funding.dto.response.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "ReviewListDto", description = "후기 목록 응답 DTO")
public class ReviewListDto {
    @Schema(description = "후기 ID", example = "1")
    private Long cmId;
    @Schema(description = "후기 내용", example = "정말 좋은 프로젝트였습니다!")
    private String cmContent;
    @Schema(description = "후기 작성일", example = "2024-06-15T14:30:00")
    private LocalDateTime createdAt;
    @Schema(description = "작성자 정보")
    private UserInfo user;
    @Schema(description = "프로젝트 정보")
    private ProjectInfo project;
    @Schema(description = "후기 이미지 URL 목록")
    private List<String> images;

    @Getter
    @Setter
    @Schema(name = "UserInfo", description = "작성자 정보 DTO")
    public static class UserInfo {
        @Schema(description = "작성자 ID", example = "42")
        private Long userId;
        @Schema(description = "작성자 닉네임", example = "creativeUser")
        private String nickname;
        @Schema(description = "작성자 프로필 이미지 URL", example = "https://example.com/profiles/user42.jpg")
        private String profileImg;
    }

    @Getter
    @Setter
    @Schema(name = "ProjectInfo", description = "프로젝트 정보 DTO")
    public static class ProjectInfo {
        @Schema(description = "프로젝트 ID", example = "1001")
        private Long projectId;
        @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치")
        private String title;
        @Schema(description = "프로젝트 썸네일 이미지 URL", example = "https://example.com/projects/thumb1001.jpg")
        private String thumbnail;
    }

    @Getter
    @Setter
    @Schema(name = "Review", description = "후기 DTO")
    public static class Review {
        @Schema(description = "후기 ID", example = "1")
        private Long cmId;
        @Schema(description = "작성자 ID", example = "42")
        private Long userId;
        @Schema(description = "프로젝트 ID", example = "1001")
        private Long projectId;
        @Schema(description = "후기 내용", example = "정말 좋은 프로젝트였습니다!")
        private String cmContent;
        @Schema(description = "후기 작성일", example = "2024-06-15T14:30:00")
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    @Schema(name = "Image", description = "후기 이미지 DTO")
    public static class Image {
        @Schema(description = "후기 ID", example = "1")
        private Long cmId;
        @Schema(description = "이미지 URL", example = "https://example.com/reviews/image1.jpg")
        private String url;
    }
}
