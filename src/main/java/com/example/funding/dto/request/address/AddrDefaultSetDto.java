package com.example.funding.dto.request.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "AddrDefaultSetDto", description = "배송지 기본 설정 DTO")
public class AddrDefaultSetDto {
    @Schema(description = "배송지 ID", example = "1")
    private Long addrId;
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    @Schema(description = "기본 배송지 설정 여부", example = "Y")
    private Character isDefault;
}
