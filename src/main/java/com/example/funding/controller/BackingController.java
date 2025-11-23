package com.example.funding.controller;

import com.example.funding.common.CustomUserPrincipal;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.backing.BackingRequestDto;
import com.example.funding.dto.request.backing.BackingRequestUpdateDto;
import com.example.funding.dto.response.backing.BackingResponseDto;
import com.example.funding.dto.response.backing.MyPageBackingDetailDto;
import com.example.funding.dto.response.backing.MyPageBackingListDto;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.service.BackingService;
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

@Tag(name = "Backing Controller", description = "후원 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/backing")
@RequiredArgsConstructor
public class BackingController {
    private final BackingService backingService;

    @Operation(summary = "후원 준비", description = "프로젝트에 대한 후원 준비 정보를 반환합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 후원 준비 정보를 반환했습니다. BackingResponseDto 포함", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/prepare/{projectId}")
    public ResponseEntity<ResponseDto<BackingResponseDto>> prepareBacking(@Parameter(description = "후원할 프로젝트의 ID", required = true) @PathVariable Long projectId,
                                                                          @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return backingService.prepareBacking(principal.userId(), projectId);
    }

    @Operation(summary = "후원 생성", description = "새로운 후원을 생성합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 후원이 생성되었습니다.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto<String>> createBacking(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                     description = "후원 생성 요청 DTO",
                                                                     required = true,
                                                                     content = @Content(schema = @Schema(implementation = BackingRequestDto.class)))
                                                             @RequestBody BackingRequestDto requestDto,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return backingService.createBacking(principal.userId(), requestDto);
    }

    @Operation(summary = "후원 취소", description = "기존 후원을 취소합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 후원이 취소되었습니다.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "후원을 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/cancel/{backingId}")
    public ResponseEntity<ResponseDto<String>> cancelBacking(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                             @Parameter(description = "취소할 후원의 ID", required = true) @PathVariable Long backingId) {
        return backingService.cancelBacking(principal.userId(), backingId);
    }

    @Operation(summary = "후원 수정", description = "기존 후원을 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 후원이 수정되었습니다.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "후원을 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/{backingId}/update")
    public ResponseEntity<ResponseDto<String>> updateBacking(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                     description = "후원 수정 요청 DTO",
                                                                     required = true,
                                                                     content = @Content(schema = @Schema(implementation = BackingRequestUpdateDto.class)))
                                                             @RequestBody BackingRequestUpdateDto requestDto,
                                                             @Parameter(description = "수정할 후원의 ID", required = true) @PathVariable Long backingId,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return backingService.updateBacking(requestDto, backingId, principal.userId());
    }

    @Operation(summary = "마이페이지 후원 목록 조회", description = "사용자의 마이페이지 후원 목록을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 마이페이지 후원 목록을 반환했습니다. MyPageBackingListDto 리스트 포함", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/myPageBackingList")
    public ResponseEntity<ResponseDto<List<MyPageBackingListDto>>> geMyPageBackingList(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return backingService.getMyPageBackingList(principal.userId());
    }

    @Operation(summary = "마이페이지 후원 상세 조회", description = "사용자의 마이페이지 후원 상세 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 마이페이지 후원 상세 정보를 반환했습니다. MyPageBackingDetailDto 포함", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "후원을 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/myPageBackingDetail/{backingId}")
    public ResponseEntity<ResponseDto<MyPageBackingDetailDto>> geMyPageBackingDetail(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                                     @Parameter(description = "후원 ID", required = true) @PathVariable Long backingId) {
        return backingService.getMyPageBackingDetail(principal.userId(), backingId);
    }

}
