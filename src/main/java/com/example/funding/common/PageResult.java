package com.example.funding.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Schema(name = "PageResult", description = "페이지 결과 정보")
public class PageResult<T> {

    @Builder.Default
    @Schema(description = "현재 페이지의 아이템 목록")
    private List<T> items = Collections.emptyList();

    @Schema(description = "현재 페이지 번호")
    private final int page; //현재 페이지
    @Schema(description = "페이지 크기")
    private final int size; //페이지 크기
    @Schema(description = "그룹당 페이지 수")
    private final int perGroup; //그룹당 페이지 수

    @Schema(description = "전체 아이템 개수")
    private final int totalElements; //전체 개수
    @Schema(description = "전체 페이지 수")
    private final int totalPages; //전체 페이지 수

    @Schema(description = "이전 페이지 존재 여부")
    private final boolean hasPrev; //이전 페이지 여부
    @Schema(description = "다음 페이지 존재 여부")
    private final boolean hasNext; //다음 페이지 여부
    @Schema(description = "이전 페이지 번호")
    private final int prevPage; //이전 페이지 번호
    @Schema(description = "다음 페이지 번호")
    private final int nextPage; //다음 페이지 번호

    @Schema(description = "현재 그룹 시작 페이지 번호")
    private final int groupStart; //현재 그룹 시작 페이지
    @Schema(description = "현재 그룹 끝 페이지 번호")
    private final int groupEnd; //현재 그룹 끝 페이지

    public static <T> PageResult<T> of(List<T> items, Pager pager, int totalElements) {
        List<T> safeItems = (items == null) ? Collections.emptyList() : items;

        int page = pager.getPage();
        int size = pager.getSize();
        int perGroup = pager.getPerGroup();

        int totalPages = (totalElements <= 0) ? 0 : (int) Math.ceil(totalElements / (double) size);

        //아이템이 하나도 없으면 무조건 전체 페이지 수 1로 고정
        int fixedPage = (totalPages == 0) ? 1 : Math.min(page, totalPages);

        boolean hasPrev = totalPages > 0 && fixedPage > 1;
        boolean hasNext = totalPages > 0 && fixedPage < totalPages;
        int prevPage = hasPrev ? (fixedPage - 1) : 1;
        int nextPage = hasNext ? (fixedPage + 1) : (totalPages == 0 ? 1 : totalPages);

        int groupIndex = (int) Math.ceil(page / (double) perGroup);
        int groupStart = (groupIndex - 1) * perGroup + 1;
        int groupEnd = (totalPages == 0) ? 1 : Math.min(groupStart + perGroup - 1, totalPages);

        return PageResult.<T>builder()
                .items(items)
                .page(page)
                .size(size)
                .perGroup(perGroup)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .hasPrev(hasPrev)
                .hasNext(hasNext)
                .prevPage(prevPage)
                .nextPage(nextPage)
                .groupStart(groupStart)
                .groupEnd(groupEnd)
                .build();
    }
}
