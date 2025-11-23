package com.example.funding.controller;

import com.example.funding.dto.ResponseDto;
import com.example.funding.handler.GlobalExceptionHandler;
import com.example.funding.model.Category;
import com.example.funding.model.Subcategory;
import com.example.funding.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Category Controller", description = "카테고리 관련 API")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "모든 카테고리 조회", description = "모든 카테고리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 모든 카테고리를 조회했습니다. List<Category> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))}
            ),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping
    public ResponseEntity<ResponseDto<List<Category>>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "모든 세부카테고리 조회", description = "모든 세부카테고리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 모든 세부카테고리를 조회했습니다. List<Subcategory> 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))}
            ),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.", content = {
                    @Content(schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class))
            })
    })
    @GetMapping("/subcategories")
    public ResponseEntity<ResponseDto<List<Subcategory>>> getAllSubcategories() {
        return categoryService.getAllSubcategories();
    }
}
