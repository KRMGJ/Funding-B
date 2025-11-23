package com.example.funding.dto.request.backing;

import com.example.funding.dto.request.reward.RewardBackingRequestDto;
import com.example.funding.model.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(name = "BackingRequestDto", description = "후원 생성 요청 DTO")
public class BackingRequestDto {
    @Schema(description = "후원 ID", example = "1")
    private Long backingId;
    @Schema(description = "후원 정보")
    private Backing backing;
    @Schema(description = "후원 상세 정보")
    private BackingDetail backingDetail;
    @Schema(description = "결제 수단 정보")
    private PaymentInfo paymentInfo;
    @Schema(description = "결제 정보")
    private Payment payment;
    @Schema(description = "배송지 정보")
    private Address address;
    @Schema(description = "배송 정보")
    private Shipping shipping;
    @Schema(description = "후원한 리워드 목록")
    private List<RewardBackingRequestDto> rewards;
    @Schema(description = "후원 총 금액", example = "50000")
    private Long amount;
    @Schema(description = "후원자 수", example = "100")
    private Long backerCnt;
}
