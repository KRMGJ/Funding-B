package com.example.funding.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PaymentMethods", description = "결제 수단 Enum")
public enum PaymentMethods {
    CARD, //카드 결제
    BANK_TRANSFER, //무통장 입금
    EASY_PAY, // 간편걸제
    ETC // 기타
}
