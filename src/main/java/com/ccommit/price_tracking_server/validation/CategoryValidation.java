package com.ccommit.price_tracking_server.validation;

import com.ccommit.price_tracking_server.DTO.CategoryDTO;
import com.ccommit.price_tracking_server.exception.CategoryHasChildrenException;
import com.ccommit.price_tracking_server.exception.CategoryNotFoundException;
import com.ccommit.price_tracking_server.exception.InvalidCategoryLevelException;
import com.ccommit.price_tracking_server.exception.ParentCategoryNotFoundException;
import com.ccommit.price_tracking_server.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class CategoryValidation {

    private final CategoryRepository categoryRepository;

    // 카테고리 레벨 검증
    public void validateCategoryLevel(CategoryDTO categoryDTO) {
        if (categoryDTO.getCategoryLevel() == null) {
            log.error("해당 카테고리 레벨은 없는 레벨입니다.");
            throw new InvalidCategoryLevelException();
        }
    }

    // 부모 카테고리 존재 여부 검증
    public void validateParentCategoryExistence(CategoryDTO categoryDTO) {
        if (categoryDTO.getParentCategoryId() != null &&
                !categoryRepository.existsById(categoryDTO.getParentCategoryId())) {
            log.error("해당 부모 카테고리가 존재하지 않습니다: parentCategoryId={}", categoryDTO.getParentCategoryId());
            throw new ParentCategoryNotFoundException();
        }
    }

    // 카테고리 존재 여부 검증
    public void validateCategoryExistence(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            log.error("해당 카테고리가 존재하지 않습니다: categoryId={}", categoryId);
            throw new CategoryNotFoundException();
        }
    }

    // 카테고리 하위 존재 여부 검증
    public void validateCategoryHasChildren(Long categoryId) {
        if (categoryRepository.existsByParentCategory_CategoryId(categoryId)) {
            log.error("해당 카테고리의 하위 카테고리가 존재합니다: categoryId={}", categoryId);
            throw new CategoryHasChildrenException();
        }
    }
}
