package com.example.funding.dto.response.creator;

import com.example.funding.model.Reward;
import com.example.funding.model.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(name = "CreatorProjectDetailDto", description = "크리에이터 프로젝트 상세 응답 DTO")
public class CreatorProjectDetailDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "크리에이터 ID", example = "1")
    private Long creatorId;
    @Schema(description = "프로젝트 제목", example = "나의 첫 번째 펀딩 프로젝트")
    private String title;
    @Schema(description = "프로젝트 목표 금액", example = "1000000")
    private Integer goalAmount;
    @Schema(description = "현재 모금된 금액", example = "500000")
    private Integer currAmount;
    @Schema(description = "프로젝트 시작 날짜", example = "2024-01-01T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "프로젝트 종료 날짜", example = "2024-02-01T00:00:00")
    private LocalDateTime endDate;
    @Schema(description = "프로젝트 생성 날짜", example = "2023-12-01T12:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "프로젝트 수정 날짜", example = "2023-12-15T12:00:00")
    private LocalDateTime updatedAt;
    @Schema(description = "프로젝트 상태", example = "OPEN")
    private String projectStatus;
    @Schema(description = "프로젝트 내용", example = "이 프로젝트는 ...")
    private String content;
    @Schema(description = "프로젝트 콘텐츠 블록", example = "[{\"type\":\"text\",\"data\":\"프로젝트 소개...\"}]")
    private String contentBlocks;
    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "사업자 등록증 URL", example = "https://example.com/business_doc.jpg")
    private String businessDoc;

    @Schema(description = "서브 카테고리 ID", example = "2")
    private Long subctgrId;
    @Schema(description = "서브 카테고리 이름", example = "테크")
    private String subctgrName;
    @Schema(description = "메인 카테고리 ID", example = "1")
    private Long ctgrId;
    @Schema(description = "메인 카테고리 이름", example = "전자기기")
    private String ctgrName;

    @Schema(description = "크리에이터 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "크리에이터 사업자 번호", example = "123-45-67890")
    private String businessNum;
    @Schema(description = "크리에이터 이메일", example = "email@example.com")
    private String email;
    @Schema(description = "크리에이터 전화번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "태그 목록")
    private List<Tag> tagList;
    @Schema(description = "리워드 목록")
    private List<Reward> rewardList;
}
