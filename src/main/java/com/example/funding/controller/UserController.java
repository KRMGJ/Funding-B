package com.example.funding.controller;

import com.example.funding.common.CustomUserPrincipal;
import com.example.funding.common.PageResult;
import com.example.funding.common.Pager;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.PagerRequest;
import com.example.funding.dto.request.user.UserNicknameDto;
import com.example.funding.dto.request.user.UserPasswordDto;
import com.example.funding.dto.request.user.UserProfileImgDto;
import com.example.funding.dto.response.creator.CreatorQnaDto;
import com.example.funding.dto.response.user.LoginUserDto;
import com.example.funding.dto.response.user.MyPageLikedDto;
import com.example.funding.dto.response.user.RecentViewProject;
import com.example.funding.dto.response.user.UserSummaryDto;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.service.AuthService;
import com.example.funding.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(description = "사용자 관련 API", name = "User Controller")
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Operation(summary = "로그인 사용자 정보 조회", description = "현재 로그인한 사용자의 정보를 조회. 관리자인 경우 별도의 관리자 정보를 반환.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 사용자 정보 조회 성공. LoginUserDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/loginUser")
    public ResponseEntity<ResponseDto<LoginUserDto>> getLoginUser(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        if ("ADMIN".equalsIgnoreCase(principal.role())) {
            LoginUserDto admin = LoginUserDto.builder()
                    .userId(null)
                    .email(principal.email())
                    .nickname("관리자")
                    .role("ADMIN")
                    .build();
            return ResponseEntity.ok(ResponseDto.success(200, "관리자 로그인 사용자 정보 조회 성공", admin));
        }
        return userService.getLoginUser(principal.userId());
    }

    @Operation(summary = "최근 본 프로젝트 추가", description = "사용자가 최근에 본 프로젝트를 추가합니다. 비회원인 경우에도 동작합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최근 본 프로젝트 추가 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자 또는 프로젝트가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/recentView/{projectId}")
    public ResponseEntity<ResponseDto<?>> addRecentViewProject(@Parameter(description = "프로젝트 ID", example = "1", required = true) @PathVariable Long projectId,
                                                               @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        Long userId = principal != null ? principal.userId() : null;
        if (userId == null) return ResponseEntity.ok(ResponseDto.success(200, "비회원 최근 본 프로젝트 추가 성공", null));
        return userService.addRecentViewProject(userId, projectId);
    }

    @Operation(summary = "최근 본 프로젝트 목록 조회", description = "사용자가 최근에 본 프로젝트 목록을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최근 본 프로젝트 목록 조회 성공. RecentViewProject 리스트 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/recentViewProjects")
    public ResponseEntity<ResponseDto<List<RecentViewProject>>> getRecentViewProjects(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                                      @Parameter(description = "조회할 최근 본 프로젝트 수") @RequestParam(required = false) Integer limit) {
        return userService.getRecentViewProjects(principal.userId(), limit != null ? limit : 10);
    }

    @Operation(summary = "닉네임 수정", description = "사용자의 닉네임을 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 수정 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "409", description = "중복된 닉네임", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/nickname")
    public ResponseEntity<ResponseDto<String>> updateNickname(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "닉네임 수정 요청 DTO", required = true,
                                                                      content = @Content(schema = @Schema(implementation = UserNicknameDto.class))) @Valid @RequestBody UserNicknameDto dto,
                                                              @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return userService.userNickname(principal.userId(), dto);
    }

    @Operation(summary = "프로필 이미지 수정", description = "사용자의 프로필 이미지를 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 이미지 수정 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/profileImg")
    public ResponseEntity<ResponseDto<String>> updateProfileImg(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "프로필 이미지 수정 요청 DTO", required = true,
                                                                        content = @Content(schema = @Schema(implementation = UserProfileImgDto.class))) @Valid @ModelAttribute UserProfileImgDto dto,
                                                                @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) throws Exception {
        return userService.userProfileImg(principal.userId(), dto);
    }

    @Operation(summary = "비밀번호 수정", description = "사용자의 비밀번호를 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 수정 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/password")
    public ResponseEntity<ResponseDto<String>> updatePassword(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "비밀번호 수정 요청 DTO", required = true,
                                                                      content = @Content(schema = @Schema(implementation = UserPasswordDto.class))) @Valid @RequestBody UserPasswordDto dto,
                                                              @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return userService.userPassword(principal.userId(), dto);
    }

    @Operation(summary = "사용자 좋아요 목록 조회", description = "사용자가 좋아요한 프로젝트 목록을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 좋아요 목록 조회 성공. MyPageLikedDto 리스트 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/likedList")
    public ResponseEntity<ResponseDto<List<MyPageLikedDto>>> getLikedList(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return userService.getLikedList(principal.userId());
    }

    @Operation(summary = "사용자 Q&A 목록 조회", description = "사용자가 작성한 Q&A 목록을 페이징 처리하여 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 Q&A 목록 조회 성공. PageResult<CreatorQnaDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/qna")
    public ResponseEntity<ResponseDto<PageResult<CreatorQnaDto>>> getQnaListOfUser(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                                   @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return userService.getQnaListOfUser(principal.userId(), pager);
    }

    @Operation(summary = "프로젝트 좋아요", description = "사용자가 특정 프로젝트를 좋아요합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 좋아요 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자 또는 프로젝트가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "409", description = "이미 좋아요한 프로젝트", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/like/{projectId}")
    public ResponseEntity<ResponseDto<Long>> likeProject(@Parameter(description = "프로젝트 ID", example = "1", required = true) @PathVariable Long projectId,
                                                         @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return userService.likeProject(principal.userId(), projectId);
    }

    @Operation(summary = "프로젝트 좋아요 취소", description = "사용자가 특정 프로젝트의 좋아요를 취소합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 좋아요 취소 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자 또는 프로젝트가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "409", description = "좋아요하지 않은 프로젝트", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @DeleteMapping("/dislike/{projectId}")
    public ResponseEntity<ResponseDto<Long>> dislikeProject(@Parameter(description = "프로젝트 ID", example = "1", required = true) @PathVariable Long projectId,
                                                            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return userService.dislikeProject(principal.userId(), projectId);
    }

    @Operation(summary = "프로젝트 좋아요 여부 확인", description = "사용자가 특정 프로젝트를 좋아요했는지 여부를 확인합니다. 비회원인 경우 false를 반환합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 좋아요 여부 조회 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자 또는 프로젝트가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/checkLike/{projectId}")
    public ResponseEntity<ResponseDto<Boolean>> isProjectLiked(@Parameter(description = "프로젝트 ID", example = "1", required = true) @PathVariable Long projectId,
                                                               @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        Long userId = principal != null ? principal.userId() : null;
        if (userId == null) return ResponseEntity.ok(ResponseDto.success(200, "비회원 프로젝트 좋아요 여부 조회 성공", false));
        return userService.checkLikedProject(userId, projectId);
    }

    @Operation(summary = "크리에이터 팔로우", description = "사용자가 특정 크리에이터를 팔로우합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 팔로우 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자 또는 크리에이터가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "409", description = "이미 팔로우한 크리에이터", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/follow/{creatorId}")
    public ResponseEntity<ResponseDto<String>> followCreator(@Parameter(description = "크리에이터 ID", example = "1", required = true) @PathVariable Long creatorId,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return userService.followCreator(principal.userId(), creatorId);
    }

    @Operation(summary = "크리에이터 팔로우 취소", description = "사용자가 특정 크리에이터의 팔로우를 취소합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 팔로우 취소 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자 또는 크리에이터가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "409", description = "팔로우하지 않은 크리에이터", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @DeleteMapping("/unfollow/{creatorId}")
    public ResponseEntity<ResponseDto<String>> unfollowCreator(@Parameter(description = "크리에이터 ID", example = "1", required = true) @PathVariable Long creatorId,
                                                               @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return userService.unfollowCreator(principal.userId(), creatorId);
    }

    @Operation(summary = "크리에이터 팔로우 여부 확인", description = "사용자가 특정 크리에이터를 팔로우했는지 여부를 확인합니다. 비회원인 경우 false를 반환합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 팔로우 여부 조회 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자 또는 크리에이터가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/checkFollow/{creatorId}")
    public ResponseEntity<ResponseDto<Boolean>> isFollowingCreator(@Parameter(description = "크리에이터 ID", example = "1", required = true) @PathVariable Long creatorId,
                                                                   @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        Long userId = principal != null ? principal.userId() : null;
        if (userId == null) return ResponseEntity.ok(ResponseDto.success(200, "비회원 크리에이터 팔로우 여부 조회 성공", false));
        return userService.isFollowingCreator(userId, creatorId);
    }

    @Operation(summary = "사용자 요약 정보 조회", description = "사용자의 요약 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 요약 정보 조회 성공. UserSummaryDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/summary")
    public ResponseEntity<ResponseDto<UserSummaryDto>> getUserSummary(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return userService.getUserSummary(principal.userId());
    }

    @Operation(summary = "회원 탈퇴", description = "사용자가 자신의 계정을 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자가 존재하지 않음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @DeleteMapping("/withdraw")
    public ResponseEntity<ResponseDto<String>> withdrawUser(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return authService.withdrawUser(principal.userId());
    }
}
