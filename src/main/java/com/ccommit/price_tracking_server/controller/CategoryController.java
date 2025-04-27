package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CategoryDTO;
import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import com.ccommit.price_tracking_server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CommonResponseDTO<CategoryDTO>> createCategories(@Validated @RequestBody CategoryDTO categoryDTO) {
        log.info("카테고리 생성 요청: name={}", categoryDTO.getCategoryName());
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        CommonResponseDTO<CategoryDTO> response = new CommonResponseDTO<>(createdCategory, "", "", 0);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CommonResponseDTO<CategoryDTO>> updateCategories(@PathVariable("categoryId") Long categoryId, @Validated @RequestBody CategoryDTO categoryDTO) {
        log.info("카테고리 수정 요청: categoriesId={}", categoryId);
        CategoryDTO updateCategory = categoryService.updateCategory(categoryDTO, categoryId);
        CommonResponseDTO<CategoryDTO> response = new CommonResponseDTO<>(updateCategory, "", "", 0);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CommonResponseDTO<Boolean>> deletedCategories(@PathVariable("categoryId") Long categoryId) {
        log.info("카테고리 삭제 요청: categoriesId={}", categoryId);
        CommonResponseDTO<Boolean> response = new CommonResponseDTO<>(categoryService.deletedCategory(categoryId), "", "", 0);
        return ResponseEntity.ok(response);
    }
}
