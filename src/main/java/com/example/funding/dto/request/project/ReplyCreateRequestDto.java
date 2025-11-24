package com.example.funding.dto.request.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "ReplyCreateRequestDto", description = "댓글 생성 요청 DTO")
public class ReplyCreateRequestDto {
    @Schema(description = "댓글 내용", example = "이 프로젝트 정말 기대돼요!")
    private String content;
    @Schema(description = "비밀 댓글 여부", example = "Y 또는 N")
    private Character isSecret;
}
