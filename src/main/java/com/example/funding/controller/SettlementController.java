package com.example.funding.controller;

import com.example.funding.common.CustomUserPrincipal;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.response.settlement.CreatorSettlementDto;
import com.example.funding.exception.notfound.CreatorNotFoundException;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.service.SettlementService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Settlement Controller", description = "정산 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/settlement")
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;

    /**
     * <p>크리에이터 ID로 정산 정보 조회</p>
     *
     * @param principal 인증된 사용자 정보
     * @return 정산 정보
     * @throws CreatorNotFoundException 크리에이터를 찾을 수 없을 때 (404)
     * @author 장민규
     * @since 2025-10-13
     */
    @Operation(summary = "크리에이터 ID로 정산 정보 조회", description = "인증된 사용자의 크리에이터 ID로 정산 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정산 정보 조회 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/creator")
    public ResponseEntity<ResponseDto<CreatorSettlementDto>> getSettlementByCreatorId(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return settlementService.getSettlementByCreatorId(principal.creatorId());
    }
}
