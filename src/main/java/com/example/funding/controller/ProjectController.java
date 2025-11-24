package com.example.funding.controller;

import com.example.funding.common.CursorPage;
import com.example.funding.common.CustomUserPrincipal;
import com.example.funding.common.PageResult;
import com.example.funding.common.Pager;
import com.example.funding.dto.ResponseDto;
import com.example.funding.dto.request.PagerRequest;
import com.example.funding.dto.request.project.CommunityCreateRequestDto;
import com.example.funding.dto.request.project.QnaAddRequestDto;
import com.example.funding.dto.request.project.ReplyCreateRequestDto;
import com.example.funding.dto.request.project.SearchProjectDto;
import com.example.funding.dto.response.project.*;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.service.CommunityService;
import com.example.funding.service.ProjectService;
import com.example.funding.service.QnaService;
import com.example.funding.service.ReplyService;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Project Controller", description = "프로젝트 관련 API")
@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final CommunityService communityService;
    private final ReplyService replyService;
    private final QnaService qnaService;

    @Operation(summary = "프로젝트 상세 페이지 조회", description = "프로젝트 상세 페이지 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 프로젝트 상세 정보를 조회했습니다. ProjectDetailDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/{projectId:\\d+}")
    public ResponseEntity<ResponseDto<ProjectDetailDto>> getProjectDetail(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId) {
        return projectService.getProjectDetail(projectId);
    }

    @Operation(summary = "최근 등록된 프로젝트 상위 10개 조회", description = "최근 등록된 프로젝트 상위 10개를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 최근 등록된 프로젝트 상위 10개를 조회했습니다. List<RecentTop10ProjectDto> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "404", description = "최근 24시간 내 결제된 프로젝트가 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/recent-top10")
    public ResponseEntity<ResponseDto<List<RecentTop10ProjectDto>>> getRecentTop10() {
        return projectService.getRecentTop10();
    }

    @Operation(summary = "추천 프로젝트 조회", description = "최근 N일 이내 시작된 추천 프로젝트를 최대 조회 개수만큼 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 추천 프로젝트를 조회했습니다. List<FeaturedProjectDto> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "404", description = "추천 프로젝트가 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/featured")
    public ResponseEntity<ResponseDto<List<FeaturedProjectDto>>> getFeatured(
            @Parameter(description = "최근 N일 이내 시작된 프로젝트를 조회합니다. 기본값은 30일입니다.", example = "30") @RequestParam(defaultValue = "30") int days,
            @Parameter(description = "최대 조회 개수입니다. 기본값은 8개입니다.", example = "8") @RequestParam(defaultValue = "8") int limit) {
        return projectService.getFeatured(days, limit);
    }

    @Operation(summary = "프로젝트 검색", description = "제목, 내용, 창작자명, 태그로 프로젝트를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 프로젝트를 검색했습니다. PageResult<FeaturedProjectDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<PageResult<FeaturedProjectDto>>> searchProject(@ParameterObject SearchProjectDto dto,
                                                                                     @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return projectService.searchProject(dto, pager);
    }

    @Operation(summary = "다가오는 프로젝트 검색", description = "다가오는 프로젝트를 제목, 내용, 창작자명, 태그로 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 다가오는 프로젝트를 검색했습니다. PageResult<FeaturedProjectDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/search/upcoming")
    public ResponseEntity<ResponseDto<PageResult<FeaturedProjectDto>>> searchUpcomingProjects(@ParameterObject SearchProjectDto dto, @ParameterObject @Valid PagerRequest req) {
        Pager pager = Pager.ofRequest(req.getPage(), req.getSize(), req.getPerGroup());
        return projectService.searchUpcomingProjects(dto, pager);
    }

    @Operation(summary = "프로젝트 상세 페이지 - 커뮤니티 목록 조회", description = "프로젝트 상세 페이지에서 커뮤니티 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 커뮤니티 목록을 조회했습니다. CursorPage<CommunityDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/{projectId}/community")
    public ResponseEntity<ResponseDto<CursorPage<CommunityDto>>> getCommunityList(
            @Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
            @Parameter(description = "마지막 항목의 생성일시", example = "2023-10-01T12:00:00") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
            @Parameter(description = "마지막 항목의 cmId", example = "100") @RequestParam(required = false) Long lastId,
            @Parameter(description = "한 번에 가져올 항목 수", example = "10") @RequestParam(defaultValue = "10") int size) {
        return communityService.getCommunityList(projectId, "CM", lastCreatedAt, lastId, size);
    }

    @Operation(summary = "프로젝트 상세 페이지 - 후기 목록 조회", description = "프로젝트 상세 페이지에서 후기 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 후기 목록을 조회했습니다. CursorPage<ReviewDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/{projectId}/review")
    public ResponseEntity<ResponseDto<CursorPage<ReviewDto>>> getReviewList(
            @Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
            @Parameter(description = "마지막 항목의 생성일시", example = "2023-10-01T12:00:00") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
            @Parameter(description = "마지막 항목의 cmId", example = "100") @RequestParam(required = false) Long lastId,
            @Parameter(description = "한 번에 가져올 항목 수", example = "10") @RequestParam(defaultValue = "10") int size) {
        return communityService.getReviewList(projectId, "RV", lastCreatedAt, lastId, size);
    }

    @Operation(summary = "프로젝트 상세 페이지 - 커뮤니티 등록", description = "프로젝트 상세 페이지에서 커뮤니티를 등록합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 커뮤니티를 등록했습니다. String 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/{projectId}/community/new")
    public ResponseEntity<ResponseDto<String>> createCommunity(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                       description = "CommunityCreateRequestDto",
                                                                       required = true,
                                                                       content = @Content(schema = @Schema(implementation = CommunityCreateRequestDto.class))
                                                               ) @RequestBody CommunityCreateRequestDto dto,
                                                               @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return communityService.createCommunity(projectId, dto, principal.userId());
    }

    @Operation(summary = "프로젝트 상세 페이지 - 커뮤니티 댓글 목록 조회", description = "프로젝트 상세 페이지에서 커뮤니티 댓글 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 커뮤니티 댓글 목록을 조회했습니다. CursorPage<ReplyDto> 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "커뮤니티를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/community/{cmId}/reply")
    public ResponseEntity<ResponseDto<CursorPage<ReplyDto>>> getReplyList(
            @Parameter(description = "커뮤니티 ID", required = true) @PathVariable Long cmId,
            @Parameter(description = "마지막 항목의 생성일시", example = "2023-10-01T12:00:00") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
            @Parameter(description = "마지막 항목의 id", example = "100") @RequestParam(required = false) Long lastId,
            @Parameter(description = "한 번에 가져올 항목 수", example = "10") @RequestParam(defaultValue = "10") int size) {
        return replyService.getReplyList(cmId, lastCreatedAt, lastId, size);
    }

    @Operation(summary = "프로젝트 상세 페이지 - 커뮤니티 댓글 등록", description = "프로젝트 상세 페이지에서 커뮤니티 댓글을 등록합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 커뮤니티 댓글을 등록했습니다. ReplyDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "커뮤니티를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/community/{cmId}/reply")
    public ResponseEntity<ResponseDto<ReplyDto>> createCommunityReply(@Parameter(description = "커뮤니티 ID", required = true) @PathVariable Long cmId,
                                                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                              description = "ReplyCreateRequestDto",
                                                                              required = true,
                                                                              content = @Content(schema = @Schema(implementation = ReplyCreateRequestDto.class))
                                                                      ) @RequestBody ReplyCreateRequestDto dto,
                                                                      @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return replyService.createCommunityReply(cmId, dto, principal.userId());
    }

    @Operation(summary = "프로젝트 상세 페이지 - 좋아요 수 조회", description = "프로젝트 상세 페이지에서 좋아요 수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 좋아요 수를 조회했습니다. Long 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/{projectId}/likeCnt")
    public ResponseEntity<ResponseDto<Long>> getLikeCnt(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId) {
        return projectService.getLikeCnt(projectId);
    }

    @Operation(summary = "프로젝트 상세 페이지 - 커뮤니티, 후기 수 조회", description = "프로젝트 상세 페이지에서 커뮤니티, 후기 수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 커뮤니티, 후기 수를 조회했습니다. ProjectCountsDto 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/{projectId}/counts")
    public ResponseEntity<ResponseDto<ProjectCountsDto>> getCounts(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId) {
        return projectService.getCounts(projectId);
    }

    /**
     * <p>QnA 내역 목록 조회(프로젝트 상세 페이지 기준)(필요X)</p>
     *
     * @param projectId     프로젝트 ID
     * @param lastCreatedAt 마지막 항목의 생성일시
     * @param lastId        마지막 항목의 cmId
     * @param size          한 번에 가져올 항목 수
     * @return 성공 시 200 OK
     * @author 이동혁
     * @since 2025-10-07
     */
    @GetMapping("/{projectId}/qna")
    public ResponseEntity<ResponseDto<CursorPage<QnaDto>>> getQnaListOfProject(@PathVariable("projectId") Long projectId,
                                                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
                                                                               @RequestParam(required = false) Long lastId,
                                                                               @RequestParam(defaultValue = "10") int size) {
        return qnaService.getQnaListOfProject(projectId, lastCreatedAt, lastId, size);
    }

    @Operation(summary = "프로젝트 상세 페이지 - QnA 질문 등록", description = "프로젝트 상세 페이지에서 QnA 질문을 등록합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 QnA 질문을 등록했습니다. String 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PostMapping("/{projectId}/qna/add")
    public ResponseEntity<ResponseDto<String>> addQuestion(@Parameter(description = "프로젝트 ID", required = true) @PathVariable Long projectId,
                                                           @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal,
                                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                   description = "QnaAddRequestDto",
                                                                   required = true,
                                                                   content = @Content(schema = @Schema(implementation = QnaAddRequestDto.class))
                                                           ) @RequestBody QnaAddRequestDto qnaDto) {
        return qnaService.addQuestion(projectId, principal.userId(), qnaDto);
    }
}
