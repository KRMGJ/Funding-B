package com.example.funding.controller;

import com.example.funding.common.CustomUserPrincipal;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.address.AddrAddRequestDto;
import com.example.funding.dto.request.address.AddrDefaultSetDto;
import com.example.funding.dto.request.address.AddrUpdateRequestDto;
import com.example.funding.dto.response.address.AddressResponseDto;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Address Controller", description = "배송지 관리 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/shipping")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @Operation(summary = "Get Address List", description = "사용자의 배송지 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 배송지 목록을 조회했습니다.", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/list")
    public ResponseEntity<ResponseDto<List<AddressResponseDto>>> getAddressList(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return addressService.getAddrList(principal.userId());
    }

    @Operation(summary = "Add Address", description = "새로운 배송지를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 배송지를 추가했습니다.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/add")
    public ResponseEntity<ResponseDto<String>> addAddress(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                  description = "추가할 배송지 정보",
                                                                  required = true,
                                                                  content = @Content(schema = @Schema(implementation = AddrAddRequestDto.class)))
                                                          @RequestBody AddrAddRequestDto addrDto) {
        return addressService.addAddress(principal.userId(), addrDto);
    }

    @Operation(summary = "Set Default Address", description = "기본 배송지를 설정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 기본 배송지를 설정했습니다.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/defaultAddr/{addrId}")
    public ResponseEntity<ResponseDto<String>> setDefaultAddr(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                              @Parameter(description = "기본 배송지로 설정할 주소 ID", required = true) @PathVariable Long addrId,
                                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                      description = "기본 배송지 설정 정보",
                                                                      required = true,
                                                                      content = @Content(schema = @Schema(implementation = AddrDefaultSetDto.class)))
                                                              @RequestBody AddrDefaultSetDto addrDefaultDto) {
        return addressService.defaultAddr(principal.userId(), addrId, addrDefaultDto);
    }

    @Operation(summary = "Update Address", description = "기존 배송지 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 배송지 정보를 수정했습니다.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/update/{addrId}")
    public ResponseEntity<ResponseDto<String>> updateAddress(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                             @Parameter(description = "수정할 주소 ID", required = true) @PathVariable Long addrId,
                                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                     description = "수정할 배송지 정보",
                                                                     required = true,
                                                                     content = @Content(schema = @Schema(implementation = AddrUpdateRequestDto.class)))
                                                             @RequestBody AddrUpdateRequestDto addrDto) {
        return addressService.updateAddr(principal.userId(), addrId, addrDto);
    }

    @Operation(summary = "Delete Address", description = "기존 배송지 정보를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 배송지 정보를 삭제했습니다.", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @DeleteMapping("/delete/{addrId}")
    public ResponseEntity<ResponseDto<String>> deleteAddress(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                             @Parameter(description = "삭제할 주소 ID", required = true) @PathVariable Long addrId) {
        return addressService.deleteAddr(principal.userId(), addrId);
    }

}
