package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CategoryDTO;
import com.ccommit.price_tracking_server.DTO.CommonResponse;
import com.ccommit.price_tracking_server.enums.SuccessDetailMessage;
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
    public ResponseEntity<CommonResponse<CategoryDTO>> createCategories(@Validated @RequestBody CategoryDTO categoryDTO) {
        log.info("카테고리 생성 요청: name={}", categoryDTO.getCategoryName());

        // SuccessDetailMessage 사용하여 응답 메시지 설정
        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_CATEGORY_CREATED;
        CommonResponse<CategoryDTO> response = new CommonResponse<>(message.name(), message.getMessage(), categoryService.createCategory(categoryDTO), 0);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CommonResponse<CategoryDTO>> updateCategories(@PathVariable("categoryId") Long categoryId, @Validated @RequestBody CategoryDTO categoryDTO) {
        log.info("카테고리 수정 요청: categoryId={}", categoryId);

        // SuccessDetailMessage 사용하여 응답 메시지 설정
        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_CATEGORY_UPDATED;
        CommonResponse<CategoryDTO> response = new CommonResponse<>(message.name(), message.getMessage(), categoryService.updateCategory(categoryDTO, categoryId), 0);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CommonResponse<Boolean>> deletedCategories(@PathVariable("categoryId") Long categoryId) {
        log.info("카테고리 삭제 요청: categoryId={}", categoryId);

        // SuccessDetailMessage 사용하여 응답 메시지 설정
        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_CATEGORY_DELETED;
        CommonResponse<Boolean> response = new CommonResponse<>(message.name(), message.getMessage(), categoryService.deletedCategory(categoryId), 0);

        return ResponseEntity.ok(response);
    }
}
