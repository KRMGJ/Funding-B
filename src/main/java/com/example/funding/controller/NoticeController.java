package com.example.funding.controller;

import com.example.funding.common.PageResult;
import com.example.funding.common.Pager;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.PagerRequest;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.model.Notice;
import com.example.funding.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notice Controller", description = "공지사항 관련 API")
@RestController
@RequestMapping("/api/v1/cs/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 페이징 처리하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 목록 조회 성공. PageResult<Notice> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/list")
    public ResponseEntity<ResponseDto<PageResult<Notice>>> noticeList(@ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return noticeService.noticeList(pager);
    }

    @Operation(summary = "공지사항 상세 조회", description = "공지사항 ID로 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 상세 조회 성공. Notice 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/{noticeId}")
    public ResponseEntity<ResponseDto<Notice>> item(@Parameter(description = "공지사항 ID", required = true) @PathVariable Long noticeId) {
        return noticeService.item(noticeId);
    }
}
