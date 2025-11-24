package com.example.funding.controller;

import com.example.funding.common.CustomUserPrincipal;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.payment.AddCardDto;
import com.example.funding.dto.response.payment.CardListDto;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payment Controller", description = "결제 수단 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제 수단 조회", description = "사용자의 등록된 결제 수단을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 결제 수단을 조회했습니다. List<CardListDto> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class))),
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/cardList")
    public ResponseEntity<ResponseDto<List<CardListDto>>> cardList(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return paymentService.getCardList(principal.userId());
    }

    @Operation(summary = "결제 수단 추가", description = "사용자의 결제 수단을 추가합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 결제 수단을 추가했습니다. String 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class)),
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/addCardList")
    public ResponseEntity<ResponseDto<String>> addCard(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                               description = "추가할 결제 수단 정보",
                                                               required = true,
                                                               content = @Content(schema = @Schema(implementation = AddCardDto.class))
                                                       ) @RequestBody AddCardDto requestCard) {
        return paymentService.addCard(principal.userId(), requestCard);
    }

    @Operation(summary = "결제 수단 삭제", description = "사용자의 결제 수단을 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 결제 수단을 삭제했습니다. String 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class)),
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @DeleteMapping("/deleteCard/{payInfoId}")
    public ResponseEntity<ResponseDto<String>> deleteCard(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                          @Parameter(description = "삭제할 결제 수단 ID", required = true) @PathVariable Long payInfoId) {
        return paymentService.deleteCard(principal.userId(), payInfoId);
    }

}
