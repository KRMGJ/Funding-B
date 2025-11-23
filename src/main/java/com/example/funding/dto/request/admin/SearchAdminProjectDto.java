package com.example.funding.dto.request.admin;

import com.example.funding.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Schema(name = "SearchAdminProjectDto", description = "관리자 프로젝트 검색 DTO")
public class SearchAdminProjectDto {
    @Schema(description = "프로젝트 상태 목록")
    private List<ProjectStatus> projectStatus;
    @Schema(description = "시작 날짜", example = "2024-01-01")
    private LocalDate fromDate;
    @Schema(description = "종료 날짜", example = "2024-12-31")
    private LocalDate toDate;
    @Schema(description = "사전 정의된 기간 유형 (예: '7d', '30d', '90d')", example = "30d")
    private String rangeType;

    public void applyRangeType() {
        if (rangeType != null) {
            LocalDate today = LocalDate.now();

            switch (rangeType) {
                case "7d" -> {
                    fromDate = today.minusDays(7);
                    toDate = today;
                }
                case "30d" -> {
                    fromDate = today.minusDays(30);
                    toDate = today;
                }
                case "90d" -> {
                    fromDate = today.minusDays(90);
                    toDate = today;
                }
            }
        }
    }

    public LocalDate getToDateEndExclusive() {
        return toDate == null ? null : toDate.plusDays(1);
    }
}
