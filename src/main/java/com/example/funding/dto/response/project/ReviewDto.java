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
@Schema(name = "ReviewDto", description = "리뷰 정보를 나타내는 DTO")
public class ReviewDto {
    @Schema(description = "리뷰 ID", example = "1")
    private Long cmId;
    @Schema(description = "리뷰 내용", example = "정말 좋은 프로젝트였습니다!")
    private String cmContent;
    @Schema(description = "리뷰 작성일", example = "2023-10-01T12:34:56")
    private LocalDateTime createdAt;
    @Schema(description = "테이블 코드")
    private String code;
    @Schema(description = "리뷰 평점", example = "5")
    private Integer rating;
    @Schema(description = "리뷰 작성자 닉네임", example = "happyUser")
    private String nickname;
    @Schema(description = "리뷰 작성자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImg;
    @Schema(description = "리뷰에 대한 답글 개수", example = "2")
    private Long replyCnt;
}
