package com.example.funding.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(name = "CursorPage", description = "Cursor 기반 페이징 처리된 데이터 응답 객체")
public class CursorPage<T> {
    @Schema(description = "현재 페이지의 아이템 목록", example = "[{}, {}]")
    private final List<T> items;
    @Schema(description = "다음 페이지의 커서", example = "eyJpZCI6MTIzLCJ0aW1lc3RhbXAiOiIyMDI0LTA2LTAxVDEyOjAwOjAwIn0=")
    private final Cursor nextCursor;
    @Schema(description = "다음 페이지가 존재하는지 여부", example = "true")
    private final boolean hasNext;
    @Schema(description = "전체 아이템 수 (선택적)", example = "100")
    private final Long totalCount;

    public CursorPage(List<T> items, Cursor nextCursor) {
        this(items, nextCursor, nextCursor != null, null);
    }

    public static <T> CursorPage<T> of(List<T> items, Cursor cursor) {
        return new CursorPage<>(items, cursor);
    }

    public static <T> CursorPage<T> empty() {
        return new CursorPage<>(List.of(), null, false, 0L);
    }
}
