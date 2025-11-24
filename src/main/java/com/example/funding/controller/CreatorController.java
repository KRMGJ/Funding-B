package com.example.funding.controller;

import com.example.funding.common.*;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.PagerRequest;
import com.example.funding.dto.request.creator.*;
import com.example.funding.dto.request.reward.RewardCreateRequestDto;
import com.example.funding.dto.request.shipping.ShippingStatusDto;
import com.example.funding.dto.response.backing.BackingCreatorProjectListDto;
import com.example.funding.dto.response.creator.*;
import com.example.funding.dto.response.shipping.CreatorShippingBackerList;
import com.example.funding.dto.response.shipping.CreatorShippingProjectList;
import com.example.funding.enums.CreatorType;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.model.Creator;
import com.example.funding.model.Reward;
import com.example.funding.service.CreatorService;
import com.example.funding.service.NewsService;
import com.example.funding.service.RewardService;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Creator Controller", description = "크리에이터 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/creator")
@RequiredArgsConstructor
public class CreatorController {

    private final CreatorService creatorService;
    private final RewardService rewardService;
    private final NewsService newsService;
    private final FileUploader fileUploader;

    @Operation(summary = "크리에이터 등록", description = "사용자를 크리에이터로 등록합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 등록 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<Long>> registerCreator(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                     description = "크리에이터 등록 요청 DTO",
                                                                     required = true,
                                                                     content = @Content(schema = @Schema(implementation = CreatorRegisterRequestDto.class)
                                                                     )) @Valid @ModelAttribute CreatorRegisterRequestDto dto,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal
    ) throws Exception {
        MultipartFile file = dto.getProfileImg();
        if (file != null && file.isEmpty()) {
            file = null;
        }
        CreatorType type = dto.getCreatorType() != null ? dto.getCreatorType() : CreatorType.GENERAL;
        dto.setProfileImg(file);
        dto.setCreatorType(type);
        return creatorService.registerCreator(dto, principal.userId());
    }

    @Operation(summary = "크리에이터 상세 정보 조회", description = "인증된 크리에이터의 상세 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 정보 조회 성공. Creator 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/info")
    public ResponseEntity<ResponseDto<Creator>> item(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return creatorService.item(principal.creatorId());
    }

    @Operation(summary = "크리에이터 정보 수정", description = "인증된 크리에이터의 정보를 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 정보 수정 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<String>> updateCreatorInfo(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                         description = "크리에이터 정보 수정 요청 DTO",
                                                                         required = true,
                                                                         content = @Content(schema = @Schema(implementation = CreatorUpdateRequestDto.class)
                                                                         )) @ModelAttribute CreatorUpdateRequestDto dto,
                                                                 @Parameter(description = "프로필 이미지 파일")
                                                                 @RequestParam(required = false) MultipartFile profileImg) throws Exception {
        if (profileImg != null && !profileImg.isEmpty()) {
            // 새로운 프로필 이미지가 있다면 업로드 처리
            dto.setProfileImg(profileImg);
            String profileImgUrl = fileUploader.upload(dto.getProfileImg());

            if (profileImgUrl != null && !profileImgUrl.isEmpty()) {
                dto.setProfileImgUrl(profileImgUrl);
            } else {
                dto.setProfileImgUrl(dto.getProfileImgUrl());
            }
        } else {
            dto.setProfileImgUrl(dto.getProfileImgUrl());
        }

        return creatorService.updateCreatorInfo(principal.creatorId(), dto);
    }

    @Operation(summary = "크리에이터 프로젝트 목록 조회", description = "인증된 크리에이터의 프로젝트 목록을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 프로젝트 목록 조회 성공. PageResult<CreatorProjectListDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/projects")
    public ResponseEntity<ResponseDto<PageResult<CreatorProjectListDto>>> getProjectList(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                                         @ParameterObject SearchCreatorProjectDto dto,
                                                                                         @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return creatorService.getProjectList(principal.creatorId(), dto, pager);
    }

    @Operation(summary = "프로젝트 상세 조회", description = "창작자의 프로젝트 상세 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 상세 조회 성공. CreatorProjectDetailDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ResponseDto<CreatorProjectDetailDto>> getProjectDetail(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                                                 @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return creatorService.getProjectDetail(projectId, principal.creatorId());
    }

    @Operation(summary = "프로젝트 생성", description = "창작자가 새로운 프로젝트를 생성합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 생성 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping(value = "/project/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<Long>> createProject(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                   description = "프로젝트 생성 요청 DTO",
                                                                   required = true,
                                                                   content = @Content(schema = @Schema(implementation = ProjectCreateRequestDto.class)
                                                                   )) @Valid @ModelAttribute ProjectCreateRequestDto dto,
                                                           @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) throws Exception {
        log.info("dto.getThumbnail(): {}", dto.getThumbnail());
        String thumbnailUrl = null;
        if (dto.getThumbnail() != null && !dto.getThumbnail().isEmpty()) {
            thumbnailUrl = fileUploader.upload(dto.getThumbnail());
        }
        String businessDocUrl = null;
        if (dto.getBusinessDoc() != null && !dto.getBusinessDoc().isEmpty()) {
            businessDocUrl = fileUploader.upload(dto.getBusinessDoc());
        }
        dto.setThumbnailUrl(thumbnailUrl);
        dto.setBusinessDocUrl(businessDocUrl);

        return creatorService.createProject(dto, principal.creatorId());
    }

    @Operation(summary = "프로젝트 수정", description = "창작자가 기존 프로젝트를 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 수정 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자 또는 프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/project/{projectId}")
    public ResponseEntity<ResponseDto<String>> updateProject(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                     description = "프로젝트 수정 요청 DTO",
                                                                     required = true,
                                                                     content = @Content(schema = @Schema(implementation = ProjectCreateRequestDto.class)
                                                                     )) @Valid @ModelAttribute ProjectCreateRequestDto dto,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) throws Exception {
        if (dto.getProjectId() != null && !dto.getProjectId().equals(projectId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 프로젝트 ID 입니다.");
        }
        dto.setProjectId(projectId);

        String thumbnailUrl = null;
        if (dto.getThumbnail() != null && !dto.getThumbnail().isEmpty()) {
            thumbnailUrl = fileUploader.upload(dto.getThumbnail());
        }
        String businessDocUrl = null;
        if (dto.getBusinessDoc() != null && !dto.getBusinessDoc().isEmpty()) {
            businessDocUrl = fileUploader.upload(dto.getBusinessDoc());
        }
        dto.setThumbnailUrl(thumbnailUrl);
        dto.setBusinessDocUrl(businessDocUrl);

        return creatorService.updateProject(dto, principal.creatorId());
    }

    @Operation(summary = "프로젝트 삭제", description = "창작자가 자신의 프로젝트를 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 삭제 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자 또는 프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<ResponseDto<String>> deleteProject(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return creatorService.deleteProject(projectId, principal.creatorId());
    }

    @Operation(summary = "프로젝트 심사 요청", description = "창작자가 자신의 프로젝트를 심사 요청합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 심사 요청 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자 또는 프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/project/{projectId}/submit")
    public ResponseEntity<ResponseDto<String>> verifyProject(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return creatorService.verifyProject(projectId, principal.creatorId());
    }

    @Operation(summary = "프로젝트 요약 조회", description = "창작자의 프로젝트 요약 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 요약 조회 성공. CreatorProjectSummaryDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자 또는 프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/projects/{projectId}/summary")
    public ResponseEntity<ResponseDto<CreatorProjectSummaryDto>> getProjectSummary(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                                                   @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return creatorService.getProjectSummary(projectId, principal.creatorId());
    }

    @Operation(summary = "리워드 목록 조회", description = "창작자의 프로젝트 리워드 목록을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리워드 목록 조회 성공. List<Reward> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "404", description = "창작자 또는 프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/projects/{projectId}/reward")
    public ResponseEntity<ResponseDto<List<Reward>>> getRewardListManage(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                                         @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return rewardService.getRewardListManage(projectId, principal.creatorId());
    }

    @Operation(summary = "리워드 단건 추가", description = "창작자의 프로젝트 리워드를 단건 추가합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리워드 단건 추가 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자 또는 프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/projects/{projectId}/reward")
    public ResponseEntity<ResponseDto<String>> addReward(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                         @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                 description = "리워드 생성 요청 DTO",
                                                                 required = true, content = {
                                                                 @Content(schema = @Schema(implementation = RewardCreateRequestDto.class))
                                                         }) @RequestBody RewardCreateRequestDto dto) {
        dto.setProjectId(projectId);
        return rewardService.addReward(projectId, principal.creatorId(), dto);
    }

    @Operation(summary = "창작자 프로필 요약 조회", description = "인증된 창작자의 프로필 요약 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "창작자 프로필 요약 조회 성공. CreatorProfileSummaryDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/summary")
    public ResponseEntity<ResponseDto<CreatorProfileSummaryDto>> getCreatorProfileSummary(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return creatorService.getCreatorProfileSummary(principal.creatorId());
    }

    @Operation(summary = "QnA 내역 목록 조회(창작자 기준)", description = "인증된 창작자의 QnA 내역 목록을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QnA 내역 목록 조회 성공. PageResult<CreatorQnaDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/qna")
    public ResponseEntity<ResponseDto<PageResult<CreatorQnaDto>>> getQnaListOfCreator(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                                      @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return creatorService.getQnaListOfCreator(principal.creatorId(), pager);
    }

    @Operation(summary = "창작자 대시보드 조회", description = "인증된 창작자의 대시보드 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "창작자 대시보드 조회 성공. CreatorDashboardDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/dashBoard")
    public ResponseEntity<ResponseDto<CreatorDashboardDto>> getCreatorDashBoard(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return creatorService.getCreatorDashBoard(principal.creatorId());
    }

    @Operation(summary = "후원한 프로젝트 목록 조회", description = "인증된 창작자가 후원한 프로젝트 목록을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "후원한 프로젝트 목록 조회 성공. List<BackingCreatorProjectListDto> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "404", description = "창작자를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/backingList")
    public ResponseEntity<ResponseDto<List<BackingCreatorProjectListDto>>> getBackingList(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return creatorService.getCreatorProjectList(principal.creatorId());
    }

    @Operation(summary = "배송 관리 프로젝트 목록 조회", description = "인증된 창작자의 배송 관리 프로젝트 목록을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "배송 관리 프로젝트 목록 조회 성공. List<CreatorShippingProjectList> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "404", description = "창작자를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/shippingList")
    public ResponseEntity<ResponseDto<List<CreatorShippingProjectList>>> getShippingList(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return creatorService.getCreatorShippingList(principal.creatorId());
    }

    @Operation(summary = "프로젝트별 배송 후원자 목록 조회", description = "인증된 창작자의 특정 프로젝트에 대한 배송 후원자 목록을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트별 배송 후원자 목록 조회 성공. List<CreatorShippingBackerList> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "404", description = "창작자 또는 프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/shippingBackerList/{projectId}")
    public ResponseEntity<ResponseDto<List<CreatorShippingBackerList>>> getShippingBackerList(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                                              @Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId) {
        return creatorService.getShippingBackerList(principal.creatorId(), projectId);
    }

    @Operation(summary = "배송 상태 변경", description = "인증된 창작자가 특정 프로젝트의 후원자에 대한 배송 상태를 변경합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "배송 상태 변경 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자 또는 프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/shippingBackerList/{projectId}")
    public ResponseEntity<ResponseDto<String>> setShippingStatus(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                                 @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                         description = "배송 상태 변경 DTO",
                                                                         required = true,
                                                                         content = @Content(schema = @Schema(implementation = ShippingStatusDto.class))
                                                                 ) @Valid @RequestBody ShippingStatusDto status) {
        return creatorService.setShippingStatus(projectId, principal.creatorId(), status);
    }

    @Operation(summary = "프로젝트 새소식 등록", description = "창작자가 자신의 프로젝트에 새소식을 등록합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로젝트 새소식 등록 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "창작자 또는 프로젝트를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/projects/{projectId}/news")
    public ResponseEntity<ResponseDto<String>> createNews(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                  description = "새소식 생성 요청 DTO",
                                                                  required = true,
                                                                  content = @Content(schema = @Schema(implementation = NewsCreateRequestDto.class))
                                                          ) @Valid @RequestBody NewsCreateRequestDto dto,
                                                          @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return newsService.createNews(projectId, principal.creatorId(), dto);
    }

    @Operation(summary = "크리에이터 팔로워 수 조회", description = "특정 크리에이터의 팔로워 수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 팔로워 수 조회 성공", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/followerCnt/{creatorId}")
    public ResponseEntity<ResponseDto<Long>> getFollowerCnt(@Parameter(description = "크리에이터 ID", required = true) @PathVariable Long creatorId) {
        return creatorService.getFollowerCnt(creatorId);
    }

    @Operation(summary = "크리에이터 요약 정보 조회", description = "특정 크리에이터의 요약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 요약 정보 조회 성공. CreatorSummaryDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/summary/{creatorId}")
    public ResponseEntity<ResponseDto<CreatorSummaryDto>> getCreatorSummary(@Parameter(description = "크리에이터 ID", required = true) @NotNull @Positive @PathVariable Long creatorId,
                                                                            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        Long userId = principal != null ? principal.userId() : null;
        return creatorService.getCreatorSummary(creatorId, userId);
    }

    @Operation(summary = "크리에이터의 프로젝트 목록 조회", description = "특정 크리에이터의 프로젝트 목록을 페이징 및 정렬하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 프로젝트 목록 조회 성공. CreatorProjectDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/projectsList/{creatorId}")
    public ResponseEntity<ResponseDto<PageResult<CreatorProjectDto>>> getCreatorProject(@Parameter(description = "크리에이터 ID", required = true) @NotNull @Positive @PathVariable Long creatorId,
                                                                                        @Parameter(description = "정렬 기준 (recent: 최신순, popular: 인기순)") @NotNull @RequestParam(required = false, defaultValue = "recent") String sort,
                                                                                        @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), 5);
        return creatorService.getCreatorProject(creatorId, sort, pager);
    }

    @Operation(summary = "크리에이터 리뷰 목록 조회", description = "특정 크리에이터의 리뷰 목록을 커서 기반 페이징으로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 리뷰 목록 조회 성공. ReviewListDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/reviews/{creatorId}")
    public ResponseEntity<ResponseDto<CursorPage<ReviewListDto>>> getCreatorReviews(
            @Parameter(description = "크리에이터 ID", required = true) @NotNull @Positive @PathVariable Long creatorId,
            @Parameter(description = "마지막으로 조회된 리뷰의 ID (커서)") @Positive @RequestParam(required = false) Long lastId,
            @Parameter(description = "마지막으로 조회된 리뷰의 생성일시 (커서)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
            @Parameter(description = "조회할 특정 프로젝트 ID (선택 사항)") @Positive @RequestParam(required = false) Long projectId,
            @Parameter(description = "사진이 포함된 리뷰만 조회 여부 (선택 사항)") @RequestParam(required = false, defaultValue = "false") Boolean photoOnly,
            @Parameter(description = "조회할 리뷰 개수") @NotNull @Positive @RequestParam(required = false, defaultValue = "10") int size) {
        return creatorService.getCreatorReviews(creatorId, lastCreatedAt, lastId, size, projectId, photoOnly);
    }

    @Operation(summary = "크리에이터 팔로워 목록 조회", description = "특정 크리에이터의 팔로워 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 팔로워 목록 조회 성공. CreatorFollowerDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/followers/{creatorId}")
    public ResponseEntity<ResponseDto<PageResult<CreatorFollowerDto>>> getCreatorFollowers(@Parameter(description = "크리에이터 ID", required = true) @NotNull @Positive @PathVariable Long creatorId,
                                                                                           @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                                                           @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        Long loginUserId = principal != null ? principal.userId() : null;
        return creatorService.getCreatorFollowers(creatorId, loginUserId, pager);
    }

    @Operation(summary = "크리에이터 소개 정보 조회", description = "특정 크리에이터의 소개 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 소개 정보 조회 성공. CreatorBioDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/bio/{creatorId}")
    public ResponseEntity<ResponseDto<CreatorBioDto>> getCreatorBio(@Parameter(description = "크리에이터 ID", required = true) @NotNull @Positive @PathVariable Long creatorId) {
        return creatorService.getCreatorBio(creatorId);
    }

    @Operation(summary = "크리에이터 총 카운트 조회", description = "특정 크리에이터의 총 카운트(프로젝트 수, 팔로워 수 등)를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "크리에이터 총 카운트 조회 성공. TotalCountsDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/totalCounts/{creatorId}")
    public ResponseEntity<ResponseDto<TotalCountsDto>> getTotalCounts(@Parameter(description = "크리에이터 ID", required = true) @NotNull @Positive @PathVariable Long creatorId) {
        return creatorService.getTotalCounts(creatorId);
    }
}
