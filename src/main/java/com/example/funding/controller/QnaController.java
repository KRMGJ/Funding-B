package com.example.funding.controller;

import com.example.funding.common.CursorPage;
import com.example.funding.common.CustomUserPrincipal;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.creator.QnaReplyCreateRequestDto;
import com.example.funding.dto.response.cs.QnaReplyDto;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "QnA Controller", description = "Q&A 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/qna")
@RequiredArgsConstructor
public class QnaController {

    private final ReplyService replyService;

    /**
     * <p>Q&A 답변 조회(필요없음)</p>
     *
     * @param qnaId         Q&A ID
     * @param lastCreatedAt 마지막 항목의 생성일시
     * @param lastId        마지막 항목의 id
     * @param size          한 번에 가져올 항목 수
     * @return 성공 시 200 OK
     * @author 이동혁
     * @since 2025-10-14
     */
    @GetMapping("/reply/{qnaId}")
    public ResponseEntity<ResponseDto<CursorPage<QnaReplyDto>>> getQnaReplyList(@Parameter(description = "Q&A ID", required = true) @PathVariable Long qnaId,
                                                                                @Parameter(description = "마지막 항목의 생성일시", example = "2023-10-01T12:00:00") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
                                                                                @Parameter(description = "마지막 항목의 ID", example = "100") @RequestParam(required = false) Long lastId,
                                                                                @Parameter(description = "한 번에 가져올 항목 수", example = "10") @RequestParam(defaultValue = "10") int size) {
        return replyService.getQnaReplyList(qnaId, lastCreatedAt, lastId, size);
    }

    @Operation(summary = "Q&A 답변 생성", description = "특정 Q&A에 대한 답변을 생성합니다. 크리에이터만 접근할 수 있습니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 Q&A 답변이 생성되었습니다. QnaReplyDto 객체를 반환합니다.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패 또는 권한 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/reply/{qnaId}")
    public ResponseEntity<ResponseDto<QnaReplyDto>> createQnaReply(@Parameter(description = "Q&A ID", required = true) @PathVariable Long qnaId,
                                                                   @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                           description = "Q&A 답변 생성 요청 DTO",
                                                                           required = true,
                                                                           content = @Content(schema = @Schema(implementation = QnaReplyCreateRequestDto.class)))
                                                                   @RequestBody QnaReplyCreateRequestDto dto) {
        return replyService.createQnaReply(qnaId, principal.creatorId(), dto);
    }
}
