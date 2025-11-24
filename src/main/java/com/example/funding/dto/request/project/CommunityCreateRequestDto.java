package com.example.funding.dto.request.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CommunityCreateRequestDto", description = "커뮤니티 생성 요청 DTO")
public class CommunityCreateRequestDto {
    @Schema(description = "커뮤니티 내용", example = "이 프로젝트에 대한 의견을 남겨주세요.")
    private String cmContent;
}
