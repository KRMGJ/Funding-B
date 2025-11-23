package com.example.funding.dto.response.admin.analytic;

import com.example.funding.enums.PaymentMethods;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "PaymentMethod", description = "결제 수단별 사용 현황 DTO")
public class PaymentMethod {
    @Schema(description = "결제 수단")
    private PaymentMethods paymentMethod;
    @Schema(description = "해당 결제 수단 사용 횟수")
    private Long cnt;
}
