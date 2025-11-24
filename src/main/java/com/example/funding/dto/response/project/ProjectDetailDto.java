package com.example.funding.dto.response.project;

import com.example.funding.model.News;
import com.example.funding.model.Reward;
import com.example.funding.model.Tag;
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
@Schema(name = "ProjectDetailDto", description = "프로젝트 상세 정보 응답 DTO")
public class ProjectDetailDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "창작자 ID", example = "1")
    private Long creatorId;
    @Schema(description = "서브 카테고리 ID", example = "1")
    private Long subctgrId;

    //프로젝트
    @Schema(description = "프로젝트 제목", example = "나만의 멋진 프로젝트")
    private String title;
    @Schema(description = "목표 금액", example = "1000000")
    private Integer goalAmount;
    @Schema(description = "현재 모금액", example = "500000")
    private Integer currAmount;
    @Schema(description = "시작 날짜", example = "2024-01-01T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "종료 날짜", example = "2024-02-01T00:00:00")
    private LocalDateTime endDate;
    @Schema(description = "프로젝트 한 줄 소개", example = "이 프로젝트는 정말 멋집니다!")
    private String content;
    @Schema(description = "프로젝트 상세 내용 블록", example = "[{...}, {...}]")
    private String contentBlocks;
    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "프로젝트 상태", example = "OPEN")
    private String projectStatus;
    @Schema(description = "후원자 수", example = "100")
    private Integer backerCnt;
    @Schema(description = "좋아요 수", example = "250")
    private Integer likeCnt;
    @Schema(description = "조회 수", example = "1000")
    private Integer viewCnt;

    //계산 필드
    @Schema(description = "달성률", example = "50")
    private Integer percentNow;
    @Schema(description = "프로젝트 개수", example = "5")
    private Integer projectCnt;
    @Schema(description = "결제 날짜", example = "2024-01-15T12:00:00")
    private LocalDateTime paymentDate;

    //창작자
    @Schema(description = "창작자 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "창작자 팔로워 수", example = "300")
    private Long followerCnt;
    @Schema(description = "창작자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImg;

    //카테고리
    @Schema(description = "카테고리 이름", example = "테크")
    private String ctgrName;
    @Schema(description = "서브 카테고리 이름", example = "가젯")
    private String subctgrName;
    //태그
    @Schema(description = "태그 리스트")
    private List<Tag> tagList;
    //리워드
    @Schema(description = "리워드 리스트")
    private List<Reward> rewardList;
    //새소식
    @Schema(description = "새소식 리스트")
    private List<News> newsList;
}
