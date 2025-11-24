package com.example.funding.dto.response.creator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "CreatorProfileSummaryDto", description = "크리에이터 프로필 요약 정보 DTO")
public class CreatorProfileSummaryDto {
    @Schema(description = "크리에이터 ID", example = "1")
    private Long creatorId;
    @Schema(description = "크리에이터 이름", example = "홍길동")
    private String creatorName;
    @Schema(description = "사업자 번호", example = "123-45-67890")
    private String businessNum;
    @Schema(description = "이메일", example = "email@example.com")
    private String email;
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;
}
