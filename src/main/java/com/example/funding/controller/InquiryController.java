package com.example.funding.controller;

import com.example.funding.common.CursorPage;
import com.example.funding.common.CustomUserPrincipal;
import com.example.funding.common.PageResult;
import com.example.funding.common.Pager;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.PagerRequest;
import com.example.funding.dto.request.cs.IqrAddRequestDto;
import com.example.funding.dto.request.cs.IqrReplyCreateRequestDto;
import com.example.funding.dto.response.cs.InquiryReplyDto;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.model.Inquiry;
import com.example.funding.service.InquiryService;
import com.example.funding.service.ReplyService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Inquiry Controller", description = "1:1 문의 관련 API")
@RestController
@RequestMapping("/api/v1/cs/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final ReplyService replyService;

    @Operation(summary = "1:1 문의 내역 목록 조회(관리자 기준)", description = "1:1 문의 내역 목록을 페이징 처리하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 문의 내역 목록을 조회했습니다. PageResult<Inquiry> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/list")
    public ResponseEntity<ResponseDto<PageResult<Inquiry>>> inquiryList(@ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return inquiryService.inquiryList(pager);
    }

    @Operation(summary = "내 문의 내역 목록 조회(후원자 기준)", description = "내 문의 내역 목록을 페이징 처리하여 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 내 문의 내역 목록을 조회했습니다. PageResult<Inquiry> 반환", content = {
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
    public ResponseEntity<ResponseDto<PageResult<Inquiry>>> myInquiryList(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                          @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return inquiryService.myInquiryList(principal.userId(), pager);
    }

    @Operation(summary = "1:1 문의 등록(후원자)", description = "인증된 후원자가 1:1 문의를 등록합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 1:1 문의가 등록되었습니다. String 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/add")
    public ResponseEntity<ResponseDto<String>> addInquiry(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                  description = "IqrAddRequestDto 객체",
                                                                  required = true,
                                                                  content = @Content(schema = @Schema(implementation = IqrAddRequestDto.class))
                                                          ) @RequestBody IqrAddRequestDto iqrDto) {
        return inquiryService.addInquiry(principal.userId(), iqrDto);
    }

    @Operation(summary = "문의내역 답변 조회(후원자, 관리자)", description = "문의내역에 대한 답변을 커서 기반 페이지네이션으로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 문의내역 답변을 조회했습니다. CursorPage<InquiryReplyDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "문의내역을 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/reply/{inqId}")
    public ResponseEntity<ResponseDto<CursorPage<InquiryReplyDto>>> getInquiryReplyList(@Parameter(description = "문의내역 ID", required = true) @PathVariable Long inqId,
                                                                                        @Parameter(description = "마지막 항목의 생성일시") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
                                                                                        @Parameter(description = "마지막 항목의 ID") @RequestParam(required = false) Long lastId,
                                                                                        @Parameter(description = "한 번에 가져올 항목 수") @RequestParam(defaultValue = "10") int size) {
        return replyService.getInquiryReplyList(inqId, lastCreatedAt, lastId, size);
    }

    @Operation(summary = "문의내역 답변 등록(관리자)", description = "관리자가 특정 문의내역에 대한 답변을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 문의내역 답변이 등록되었습니다. InquiryReplyDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "문의내역을 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/reply/{inqId}")
    public ResponseEntity<ResponseDto<InquiryReplyDto>> createInquiryReply(@Parameter(description = "문의내역 ID", required = true) @PathVariable Long inqId,
                                                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                                   description = "IqrReplyCreateRequestDto 객체",
                                                                                   required = true,
                                                                                   content = @Content(schema = @Schema(implementation = IqrReplyCreateRequestDto.class))
                                                                           ) @RequestBody IqrReplyCreateRequestDto dto) {
        return replyService.createInquiryReply(inqId, dto);
    }
}
