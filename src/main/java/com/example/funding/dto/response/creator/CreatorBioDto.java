package com.example.funding.dto.response.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CreatorBioDto", description = "크리에이터 소개 정보")
public class CreatorBioDto {
    @Schema(description = "크리에이터 ID", example = "1")
    private Long creatorId;
    @Schema(description = "크리에이터 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "크리에이터 소개", example = "안녕하세요, 저는 크리에이터 홍길동입니다.")
    private String bio;
}
