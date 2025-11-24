package com.example.funding.dto.response.backing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "BackingCreatorBackerList", description = "크리에이터가 본인의 후원자 목록을 조회할 때 사용하는 DTO")
public class BackingCreatorBackerList {

    // 유저
    @Schema(description = "유저 아이디", example = "1")
    private Long userId;
    @Schema(description = "유저 닉네임", example = "멋진후원자")
    private String nickname;

    //후원
    @Schema(description = "후원 금액", example = "50000")
    private Long amount;
    @Schema(description = "후원 일시", example = "2023-10-01T12:34:56")
    private LocalDateTime createdAt;

    @Schema(description = "프로젝트 아이디", example = "1")
    private Long projectId;
}
