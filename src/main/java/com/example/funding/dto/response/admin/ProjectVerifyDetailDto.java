package com.example.funding.dto.response.admin;

import com.example.funding.model.Reward;
import com.example.funding.model.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(name = "ProjectVerifyDetailDto", description = "관리자 - 프로젝트 검수 상세 응답 DTO")
public class ProjectVerifyDetailDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "프로젝트 제목", example = "혁신적인 스마트 워치 개발 프로젝트")
    private String title;
    @Schema(description = "목표 금액", example = "50000000")
    private Integer goalAmount;
    @Schema(description = "프로젝트 시작일", example = "2024-07-01T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "프로젝트 종료일", example = "2024-08-01T00:00:00")
    private LocalDateTime endDate;
    @Schema(description = "프로젝트 간략 설명", example = "최신 기술이 접목된 스마트 워치로 건강 관리를 혁신합니다.")
    private String content;
    @Schema(description = "프로젝트 상세 내용 블록", example = "[{\"type\":\"text\",\"data\":{\"text\":\"상세 내용 예시\"}}]")
    private String contentBlocks;
    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnail;
    @Schema(description = "사업자 등록증 URL", example = "https://example.com/business_doc.jpg")
    private String businessDoc;
    @Schema(description = "프로젝트 상태", example = "PENDING")
    private String projectStatus;
    @Schema(description = "검수 요청 일시", example = "2024-06-15T10:30:00")
    private LocalDateTime requestedAt;

    @Schema(description = "서브 카테고리 이름", example = "웨어러블")
    private String subctgrName;
    @Schema(description = "메인 카테고리 이름", example = "전자기기")
    private String ctgrName;

    @Schema(description = "제작자 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "제작자 사업자 번호", example = "123-45-67890")
    private String businessNum;
    @Schema(description = "제작자 이메일", example = "email@example.com")
    private String email;
    @Schema(description = "제작자 전화번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "태그 리스트")
    private List<Tag> tagList;
    @Schema(description = "리워드 리스트")
    private List<Reward> rewardList;
}
