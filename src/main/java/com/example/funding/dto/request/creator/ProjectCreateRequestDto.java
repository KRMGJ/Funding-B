package com.example.funding.dto.request.creator;

import com.example.funding.dto.request.reward.RewardCreateRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(name = "ProjectCreateRequestDto", description = "프로젝트 생성 요청 DTO")
public class ProjectCreateRequestDto {
    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;
    @Schema(description = "서브 카테고리 ID", example = "2")
    private Long subctgrId;

    //프로젝트
    @Schema(description = "프로젝트 제목", example = "나만의 멋진 프로젝트")
    private String title;
    @Schema(description = "목표 금액", example = "1000000")
    private Integer goalAmount;
    @Schema(description = "프로젝트 시작 날짜", example = "2024-07-01T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "프로젝트 종료 날짜", example = "2024-08-01T00:00:00")
    private LocalDateTime endDate;
    @Schema(description = "프로젝트 한 줄 소개", example = "이 프로젝트는 정말 멋집니다!")
    private String content;
    @Schema(description = "프로젝트 상세 내용 블록", example = "[{...}, {...}]")
    private String contentBlocks;
    @Schema(description = "썸네일 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnailUrl;
    @Schema(description = "사업자 등록증 URL", example = "https://example.com/business_doc.pdf")
    private String businessDocUrl;

    //태그/리워드
    @Schema(description = "태그 리스트", example = "[\"테크\", \"혁신\"]")
    private List<String> tagList;
    @Schema(description = "리워드 리스트")
    private List<RewardCreateRequestDto> rewardList;

    //파일
    @Schema(description = "썸네일 이미지 파일")
    private MultipartFile thumbnail;
    @Schema(description = "사업자 등록증 파일")
    private MultipartFile businessDoc;
}
