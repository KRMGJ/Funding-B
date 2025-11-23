package com.example.funding.controller;

import com.example.funding.common.CustomUserPrincipal;
import com.example.funding.common.PageResult;
import com.example.funding.common.Pager;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.PagerRequest;
import com.example.funding.dto.request.cs.RpAddRequestDto;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.model.Report;
import com.example.funding.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report Controller", description = "신고 관련 API")
@RestController
@RequestMapping("/api/v1/cs/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "신고 내역 목록 조회(관리자 기준)", description = "신고 내역 목록을 페이징 처리하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 신고 내역 목록을 조회했습니다. Report 객체의 PageResult 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/list")
    public ResponseEntity<ResponseDto<PageResult<Report>>> reportList(@ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return reportService.reportList(pager);
    }

    @Operation(summary = "내 신고 내역 목록 조회(후원자 기준)", description = "내가 작성한 신고 내역 목록을 페이징 처리하여 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 내 신고 내역 목록을 조회했습니다. Report 객체의 PageResult 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/mylist")
    public ResponseEntity<ResponseDto<PageResult<Report>>> reportList(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                      @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return reportService.myReportList(principal.userId(), pager);
    }

    @Operation(summary = "신고 등록", description = "신고를 등록합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 신고가 등록되었습니다.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/add")
    public ResponseEntity<ResponseDto<String>> addReport(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                 description = "신고 등록 요청 DTO",
                                                                 required = true,
                                                                 content = @Content(schema = @Schema(implementation = RpAddRequestDto.class))
                                                         ) @RequestBody RpAddRequestDto rpDto) {
        return reportService.addReport(principal.userId(), rpDto);
    }

    @Operation(summary = "신고 내역 상세 조회", description = "신고 내역의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 신고 내역을 조회했습니다. Report 객체 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "해당 신고 내역을 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/{reportId}")
    public ResponseEntity<ResponseDto<Report>> item(@Parameter(description = "신고 ID", required = true) @PathVariable Long reportId) {
        return reportService.item(reportId);
    }

}
