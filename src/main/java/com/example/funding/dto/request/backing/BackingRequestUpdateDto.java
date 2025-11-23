package com.example.funding.dto.request.backing;

import com.example.funding.dto.request.address.AddrAddRequestDto;
import com.example.funding.dto.response.backing.BackingRewardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "BackingRequestUpdateDto", description = "후원 수정 요청 DTO")
public class BackingRequestUpdateDto {
    @Schema(description = "후원 ID", example = "1")
    private Long backingId;
    @Schema(description = "사용자 ID", example = "42")
    private Long userId;

    @Schema(description = "수정할 후원 리워드 목록")
    private List<BackingRewardDto> backingRewards;

    @Schema(description = "수정할 리워드 ID", example = "3")
    private Long rewardId;

    @Schema(description = "수정할 새로운 주소 정보")
    private AddrAddRequestDto newAddress;

}
