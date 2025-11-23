package com.example.funding.controller;

import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.admin.RegisterAdminRequestDto;
import com.example.funding.dto.request.auth.CheckEmailRequestDto;
import com.example.funding.dto.request.auth.CheckNicknameRequestDto;
import com.example.funding.dto.request.auth.SignInRequestDto;
import com.example.funding.dto.request.auth.SignUpRequestDto;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth Controller", description = "인증 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "사용자 회원가입을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/signUp")
    public ResponseEntity<ResponseDto<String>> signUp(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "회원가입 정보",
            required = true,
            content = @Content(schema = @Schema(implementation = SignUpRequestDto.class))
    ) @Valid @RequestBody SignUpRequestDto dto) {
        return authService.signUp(dto);
    }

    @Operation(summary = "로그인", description = "사용자 로그인을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공. JWT 토큰 발급", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 인증 정보", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/signIn")
    public ResponseEntity<ResponseDto<String>> signIn(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "로그인 정보",
            required = true,
            content = @Content(schema = @Schema(implementation = SignInRequestDto.class))
    ) @Valid @RequestBody SignInRequestDto dto) {
        return authService.signIn(dto);
    }

    @Operation(summary = "이메일 중복 확인", description = "이메일 중복 확인을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 사용 가능", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/checkEmail")
    public ResponseEntity<ResponseDto<String>> checkEmail(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "이메일 정보",
            required = true,
            content = @Content(schema = @Schema(implementation = CheckEmailRequestDto.class))
    ) @Valid @RequestBody CheckEmailRequestDto dto) {
        return authService.checkEmail(dto);
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 사용 가능", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 닉네임", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/checkNickname")
    public ResponseEntity<ResponseDto<String>> checkNickname(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "닉네임 정보",
            required = true,
            content = @Content(schema = @Schema(implementation = CheckNicknameRequestDto.class))
    ) @Valid @RequestBody CheckNicknameRequestDto dto) {
        return authService.checkNickname(dto);
    }

    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관리자 회원가입 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 관리자 아이디", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/registerAdmin")
    public ResponseEntity<ResponseDto<String>> registerAdmin(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "관리자 회원가입 정보",
            required = true,
            content = @Content(schema = @Schema(implementation = RegisterAdminRequestDto.class))
    ) @Valid @RequestBody RegisterAdminRequestDto dto) {
        return authService.registerAdmin(dto);
    }

    @Operation(summary = "관리자 로그인", description = "관리자 로그인을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관리자 로그인 성공. JWT 토큰 발급", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "잘못된 관리자 인증 정보", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/loginAdmin")
    public ResponseEntity<ResponseDto<String>> loginAdmin(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "관리자 로그인 정보",
            required = true,
            content = @Content(schema = @Schema(implementation = RegisterAdminRequestDto.class))
    ) @Valid @RequestBody RegisterAdminRequestDto dto) {
        return authService.loginAdmin(dto);
    }
}
