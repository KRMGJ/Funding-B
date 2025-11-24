package com.example.funding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Notification", description = "알림 정보")
public class Notification {
    @Schema(description = "알림 ID", example = "1")
    private Long notificationId;
    @Schema(description = "사용자 ID", example = "42")
    private Long userId;
    @Schema(description = "대상 ID", example = "1001")
    private Long targetId;
    @Schema(description = "알림 유형", example = "COMMENT")
    private String type;
    @Schema(description = "알림 메시지", example = "새 댓글이 달렸습니다.")
    private String message;
    @Schema(description = "읽음 여부", example = "Y")
    private Character isRead;
    @Schema(description = "생성 일시", example = "2024-06-15T10:15:30")
    private LocalDateTime createdAt;
}
