package com.example.funding.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "BackingStatus", description = "후원 상태 Enum")
public enum BackingStatus {
    PENDING, //결제대기
    COMPLETED, //결제완료
    CANCELED, //결제취소
    FAILED, //결제실패
    REFUNDED // 환불
}
