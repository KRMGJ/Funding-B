package com.example.funding.controller;

import com.example.funding.common.PageResult;
import com.example.funding.common.Pager;
import com.example.funding.common.Utils;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.PagerRequest;
import com.example.funding.dto.request.admin.AdminProjectUpdateDto;
import com.example.funding.dto.request.admin.RejectProjectDto;
import com.example.funding.dto.request.admin.SearchAdminProjectDto;
import com.example.funding.dto.request.admin.UserAdminUpdateRequestDto;
import com.example.funding.dto.request.category.CreateCategoryDto;
import com.example.funding.dto.request.category.CreateSubCategoryDto;
import com.example.funding.dto.request.cs.NoticeAddRequestDto;
import com.example.funding.dto.request.cs.NoticeUpdateRequestDto;
import com.example.funding.dto.request.cs.ReportUpdateRequestDto;
import com.example.funding.dto.request.settlement.SettlementPaidRequestDto;
import com.example.funding.dto.request.settlement.SettlementSearchCond;
import com.example.funding.dto.response.admin.AdminAnalyticsDto;
import com.example.funding.dto.response.admin.AdminProjectListDto;
import com.example.funding.dto.response.admin.ProjectVerifyDetailDto;
import com.example.funding.dto.response.admin.ProjectVerifyListDto;
import com.example.funding.dto.response.admin.analytic.CategorySuccess;
import com.example.funding.dto.response.admin.analytic.Kpi;
import com.example.funding.dto.response.admin.analytic.RewardSalesTop;
import com.example.funding.dto.response.settlement.SettlementItem;
import com.example.funding.dto.row.SettlementSummary;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.model.User;
import com.example.funding.service.AdminService;
import com.example.funding.service.CategoryService;
import com.example.funding.service.SettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.example.funding.common.Utils.monthsInt;
import static com.example.funding.common.Utils.resolveWindow;

@RestController
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    private final AdminService adminService;
    private final SettlementService settlementService;
    private final CategoryService categoryService;

    @Operation(summary = "관리자 대시보드 분석 데이터 조회", description = "관리자 대시보드에 필요한 분석 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "분석 데이터 조회 성공. AdminAnalyticsDto 반환",
                    content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "분석 데이터가 없음",
                    content = {@Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
                    }),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = {@Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
                    })
    })
    @GetMapping("/analytics")
    public ResponseEntity<ResponseDto<AdminAnalyticsDto>> getAdminAnalytics(
            @Parameter(description = "조회 기간 (1m, 3m, 6m, 1y, all)", example = "6m") @RequestParam(defaultValue = "6m") @NotNull String period,
            @Parameter(description = "정렬 기준 (qty: 판매 수량, revenue: 매출)", example = "qty") @RequestParam(defaultValue = "qty") @NotBlank String metric,
            @Parameter(description = "상위 N개 리워드 조회 제한", example = "5") @RequestParam(defaultValue = "5") @NotNull @Positive Integer limit,
            @Parameter(description = "카테고리 ID", example = "1") @RequestParam(defaultValue = "1") @NotNull @Positive Long ctgrId
    ) {
        Utils.AnalyticsWindow w = resolveWindow(period, metric, KST);
        return adminService.getAdminAnalytics(w.getFrom(), w.getTo(), limit, metric, w.getMonths(), ctgrId);
    }

    @Operation(summary = "특정 카테고리의 성공률 분석 데이터 조회", description = "특정 카테고리의 성공률 분석 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공률 분석 데이터 조회 성공. List<CategorySuccess> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "404", description = "성공률 분석 데이터가 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/category-success")
    public ResponseEntity<ResponseDto<List<CategorySuccess>>> getCategorySuccessByCategory(@Parameter(description = "카테고리 ID", example = "1", required = true) @RequestParam Long ctgrId) {
        return categoryService.getCategorySuccessByCategory(ctgrId);
    }

    @Operation(summary = "주요 성과 지표(KPI) 조회", description = "주요 성과 지표(KPI) 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "KPI 조회 성공. Kpi 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "KPI 데이터가 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/kpi")
    public ResponseEntity<ResponseDto<Kpi>> getKpi(@Parameter(description = "조회 기간 (1m, 3m, 6m, 1y, all)", example = "6m") @RequestParam(defaultValue = "6m") String period) {
        int months = monthsInt(period);
        return adminService.getKpi(months);
    }

    @Operation(summary = "상위 리워드 판매량/매출 조회", description = "상위 리워드 판매량/매출 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상위 리워드 판매량/매출 조회 성공. List<RewardSalesTop> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "404", description = "상위 리워드 판매량/매출 데이터가 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/reward-sales-top")
    public ResponseEntity<ResponseDto<List<RewardSalesTop>>> getRewardSalesTops(
            @Parameter(description = "조회 기간 (1m, 3m, 6m, 1y, all)", example = "6m") @RequestParam(defaultValue = "6m") String period,
            @Parameter(description = "정렬 기준 (qty: 판매 수량, revenue: 매출)", example = "qty") @RequestParam(defaultValue = "qty") String metric,
            @Parameter(description = "상위 N개 리워드 조회 제한", example = "5") @RequestParam(defaultValue = "5") int limit) {
        Utils.AnalyticsWindow w = resolveWindow(period, metric, KST);
        return adminService.getRewardSalesTops(w.getFrom(), w.getTo(), limit, metric);
    }

    /**
     * <p>프로젝트 목록 조회</p>
     *
     * @param dto SearchProjectVerifyDto
     * @param req 요청 pager
     * @return 성공 시 200 OK
     * @author 조은애
     * @since 2025-10-01
     */
    @Operation(summary = "프로젝트 목록 조회", description = "프로젝트 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 목록 조회 성공. PageResult<AdminProjectListDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/project")
    public ResponseEntity<ResponseDto<PageResult<AdminProjectListDto>>> getProjectList(@ParameterObject SearchAdminProjectDto dto, @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return adminService.getProjectList(dto, pager);
    }

    @Operation(summary = "프로젝트 취소", description = "지정된 프로젝트를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 취소 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/project/{projectId}/cancel")
    public ResponseEntity<ResponseDto<String>> cancelProject(@Parameter(description = "프로젝트 ID", example = "1", required = true) @PathVariable Long projectId) {
        return adminService.cancelProject(projectId);
    }

    @Operation(summary = "프로젝트 수정", description = "지정된 프로젝트의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 수정 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/project/{projectId}")
    public ResponseEntity<ResponseDto<String>> updateProject(@Parameter(description = "프로젝트 ID", example = "1", required = true) @PathVariable Long projectId,
                                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                     description = "프로젝트 수정 정보",
                                                                     required = true,
                                                                     content = @Content(schema = @Schema(implementation = AdminProjectUpdateDto.class))
                                                             ) @RequestBody AdminProjectUpdateDto dto) {
        dto.setProjectId(projectId);
        return adminService.updateProject(dto);
    }

    @Operation(summary = "프로젝트 심사 목록 조회", description = "심사 대기 중인 프로젝트 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 심사 목록 조회 성공. PageResult<ProjectVerifyListDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/verify")
    public ResponseEntity<ResponseDto<PageResult<ProjectVerifyListDto>>> getProjectVerifyList(@ParameterObject SearchAdminProjectDto dto, @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return adminService.getProjectVerifyList(dto, pager);
    }

    @Operation(summary = "프로젝트 심사 상세 조회", description = "지정된 프로젝트의 심사 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 심사 상세 조회 성공. ProjectVerifyDetailDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/verify/{projectId}")
    public ResponseEntity<ResponseDto<ProjectVerifyDetailDto>> getProjectVerifyDetail(@Parameter(description = "프로젝트 ID", example = "1", required = true) @PathVariable Long projectId) {
        return adminService.getProjectVerifyDetail(projectId);
    }

    @Operation(summary = "프로젝트 승인", description = "지정된 프로젝트를 승인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 승인 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/verify/{projectId}/approve")
    public ResponseEntity<ResponseDto<String>> approveProject(@Parameter(description = "프로젝트 ID", example = "1", required = true) @PathVariable Long projectId) {
        return adminService.approveProject(projectId);
    }

    @Operation(summary = "프로젝트 반려", description = "지정된 프로젝트를 반려합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 반려 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/verify/{projectId}/reject")
    public ResponseEntity<ResponseDto<String>> rejectProject(@Parameter(description = "프로젝트 ID", example = "1", required = true) @PathVariable Long projectId,
                                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                     description = "프로젝트 반려 사유",
                                                                     required = true,
                                                                     content = @Content(schema = @Schema(implementation = RejectProjectDto.class))
                                                             ) @RequestBody RejectProjectDto dto) {
        return adminService.rejectProject(projectId, dto.getRejectedReason());
    }

    @Operation(summary = "회원 관리 목록 조회", description = "회원 관리 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 관리 목록 조회 성공. PageResult<User> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/user/list")
    public ResponseEntity<ResponseDto<PageResult<User>>> userList(@ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return adminService.userList(pager);
    }

    @Operation(summary = "정산 목록 조회", description = "정산 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정산 목록 조회 성공. PageResult<SettlementItem> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/settlement/list")
    public ResponseEntity<ResponseDto<PageResult<SettlementItem>>> getSettlements(
            @Parameter(description = "검색어 (프로젝트명, 크리에이터명)", example = "프로젝트A") @RequestParam(required = false) String q,
            @Parameter(description = "정산 상태 (ALL, PENDING, COMPLETED)", example = "ALL") @RequestParam(required = false, defaultValue = "ALL") String status,
            @Parameter(description = "시작 날짜 (yyyy-MM-dd)", example = "2025-01-01") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime from,
            @Parameter(description = "종료 날짜 (yyyy-MM-dd)", example = "2025-12-31") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime to,
            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1") @RequestParam(required = false, defaultValue = "1") Integer page,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(required = false, defaultValue = "10") Integer size,
            @Parameter(description = "페이지 그룹당 페이지 수", example = "5") @RequestParam(required = false, defaultValue = "5") Integer perGroup
    ) {
        SettlementSearchCond cond = SettlementSearchCond.builder()
                .q(q)
                .status(status)
                .from(from)
                .to(to)
                .build();
        Pager pager = Pager.ofRequest(page, size, perGroup);
        return settlementService.getSettlements(cond, pager);
    }

    @Operation(summary = "정산 요약 정보 조회", description = "정산 요약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정산 요약 정보 조회 성공. SettlementSummary 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/settlement/summary")
    public ResponseEntity<ResponseDto<SettlementSummary>> getSettlementSummary() {
        return settlementService.getSettlementSummary();
    }

    @Operation(summary = "정산 상태 변경", description = "정산 상태를 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정산 상태 변경 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트 또는 정산 정보를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (프로젝트가 성공 상태가 아님 또는 이미 변경된 상태)", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/settlement")
    public ResponseEntity<ResponseDto<String>> updateStatus(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "정산 상태 변경 정보",
            required = true,
            content = @Content(schema = @Schema(implementation = SettlementPaidRequestDto.class))) @Valid @RequestBody SettlementPaidRequestDto dto) {
        return settlementService.updateStatus(dto);
    }

    @Operation(summary = "회원 정보 상세 조회", description = "지정된 회원의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 상세 조회 성공. User 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/user/info/{userId}")
    public ResponseEntity<ResponseDto<User>> item(@Parameter(description = "사용자 ID", example = "1", required = true) @PathVariable Long userId) {
        return adminService.item(userId);
    }

    @Operation(summary = "회원 정보 수정(관리자)", description = "관리자가 회원 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/user/update/{userId}")
    public ResponseEntity<ResponseDto<String>> updateNotice(@Parameter(description = "사용자 ID", example = "1", required = true) @PathVariable Long userId,
                                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                    description = "회원 정보 수정 데이터",
                                                                    required = true,
                                                                    content = @Content(schema = @Schema(implementation = UserAdminUpdateRequestDto.class))
                                                            ) @RequestBody UserAdminUpdateRequestDto userDto) {
        return adminService.updateUser(userId, userDto);
    }

    @Operation(summary = "공지사항 등록", description = "새로운 공지사항을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 등록 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/notice/add")
    public ResponseEntity<ResponseDto<String>> addNotice(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "공지사항 등록 데이터",
            required = true,
            content = @Content(schema = @Schema(implementation = NoticeAddRequestDto.class))) @RequestBody NoticeAddRequestDto ntcDto) {
        return adminService.addNotice(ntcDto);
    }

    @Operation(summary = "공지사항 수정", description = "기존 공지사항을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 수정 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/notice/update/{noticeId}")
    public ResponseEntity<ResponseDto<String>> updateNotice(@Parameter(description = "공지사항 ID", example = "1", required = true) @PathVariable Long noticeId,
                                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                    description = "공지사항 수정 데이터",
                                                                    required = true,
                                                                    content = @Content(schema = @Schema(implementation = NoticeUpdateRequestDto.class))
                                                            ) @RequestBody NoticeUpdateRequestDto ntcDto) {
        return adminService.updateNotice(noticeId, ntcDto);
    }

    @Operation(summary = "공지사항 삭제", description = "기존 공지사항을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 삭제 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @DeleteMapping("/notice/delete/{noticeId}")
    public ResponseEntity<ResponseDto<String>> deleteNotice(@Parameter(description = "공지사항 ID", example = "1", required = true) @PathVariable Long noticeId) {
        return adminService.deleteNotice(noticeId);
    }

    @Operation(summary = "신고내역 상태 수정", description = "신고내역의 상태를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "신고내역 상태 수정 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "신고내역을 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/report/update/{reportId}")
    public ResponseEntity<ResponseDto<String>> updateReportStatus(@Parameter(description = "신고 ID", example = "1", required = true) @PathVariable Long reportId,
                                                                  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                          description = "신고내역 상태 수정 데이터",
                                                                          required = true,
                                                                          content = @Content(schema = @Schema(implementation = ReportUpdateRequestDto.class))
                                                                  ) @RequestBody ReportUpdateRequestDto dto) {
        return adminService.updateReportStatus(reportId, dto);
    }

    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 생성 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/categories/create")
    public ResponseEntity<ResponseDto<String>> createCategory(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "카테고리 생성 데이터",
            required = true,
            content = @Content(schema = @Schema(implementation = CreateCategoryDto.class))) @RequestBody CreateCategoryDto dto) {
        return categoryService.createCategory(dto);
    }

    @Operation(summary = "세부카테고리 생성", description = "새로운 세부카테고리를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "세부카테고리 생성 성공.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/subcategories/create")
    public ResponseEntity<ResponseDto<String>> createSubCategory(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "세부카테고리 생성 데이터",
            required = true,
            content = @Content(schema = @Schema(implementation = CreateSubCategoryDto.class))) @RequestBody CreateSubCategoryDto dto) {
        return categoryService.createSubCategory(dto);
    }
}
