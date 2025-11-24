package com.example.funding.controller;

import com.example.funding.common.CustomUserPrincipal;
import com.example.funding.common.NotificationSseHub;
import com.example.funding.dto.ResponseDto;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.model.Notification;
import com.example.funding.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Tag(name = "Notification Controller", description = "알림 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationSseHub hub;
    private final NotificationService notificationService;

    @Operation(summary = "SSE 연결", description = "서버-발송 이벤트(SSE) 연결을 설정합니다.")
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestParam Long userId, HttpServletResponse response) {
        return hub.register(userId, response);
    }

    @Operation(summary = "모든 알림 조회", description = "인증된 사용자의 모든 알림을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 모든 알림을 조회했습니다. List<Notification> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))
            }),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/list")
    public ResponseEntity<ResponseDto<List<Notification>>> getAllNotifications(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return notificationService.getNotificationsByUserId(principal.userId());
    }

    @Operation(summary = "알림 ID로 단일 알림 조회", description = "알림 ID로 단일 알림을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 단일 알림을 조회했습니다. Notification 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자 또는 알림을 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/{notificationId}")
    public ResponseEntity<ResponseDto<Notification>> getNotificationById(@Parameter(description = "알림 ID", required = true) @PathVariable Long notificationId,
                                                                         @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return notificationService.getNotificationById(notificationId, principal.userId());
    }

    @Operation(summary = "알림 읽음 처리", description = "특정 알림을 읽음 처리합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 알림을 읽음 처리했습니다. 성공 메시지 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자 또는 알림을 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PutMapping("/read/{notificationId}")
    public ResponseEntity<ResponseDto<String>> markAsRead(@Parameter(description = "알림 ID", required = true) @PathVariable Long notificationId,
                                                          @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return notificationService.markAsRead(notificationId, principal.userId());
    }

    @Operation(summary = "모든 알림 읽음 처리", description = "인증된 사용자의 모든 알림을 읽음 처리합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 모든 알림을 읽음 처리했습니다. 성공 메시지 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @PutMapping("/readAll")
    public ResponseEntity<ResponseDto<String>> markAllAsRead(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return notificationService.markAllAsRead(principal.userId());
    }

    @Operation(summary = "알림 삭제", description = "특정 알림을 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 알림을 삭제했습니다. 성공 메시지 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자 또는 알림을 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<ResponseDto<String>> deleteNotification(@Parameter(description = "알림 ID", required = true) @PathVariable Long notificationId,
                                                                  @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return notificationService.deleteNotification(notificationId, principal.userId());
    }

    @Operation(summary = "모든 알림 삭제", description = "인증된 사용자의 모든 알림을 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 모든 알림을 삭제했습니다. 성공 메시지 반환", content = {
                    @Content(schema = @Schema(implementation = ResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @DeleteMapping("/deleteAll")
    public ResponseEntity<ResponseDto<String>> deleteAllNotifications(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal principal) {
        return notificationService.deleteAllNotificationsByUserId(principal.userId());
    }

    @ExceptionHandler({AsyncRequestNotUsableException.class, ClientAbortException.class, IOException.class})
    public ResponseEntity<Void> handleClientDisconnect(Exception ignored) {
//        log.debug("클라이언트 연결이 끊어짐: {}", ex.getMessage());
        return ResponseEntity.noContent().build();
    }
}
