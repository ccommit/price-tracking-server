package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.DTO.CategoryDTO;
import com.ccommit.price_tracking_server.entity.Category;
import com.ccommit.price_tracking_server.enums.CategoryLevel;
import com.ccommit.price_tracking_server.exception.InvalidCategoryLevelException;
import com.ccommit.price_tracking_server.mapper.CategoryMapper;
import com.ccommit.price_tracking_server.repository.CategoryRepository;
import com.ccommit.price_tracking_server.service.CategoryService;
import com.ccommit.price_tracking_server.validation.CategoryValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryValidation categoryValidation;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        categoryDTO.setCategoryLevel(fromJson(categoryDTO.getCategoryLevel()));
        categoryValidation.validateCategoryLevel(categoryDTO);
        categoryValidation.validateParentCategoryExistence(categoryDTO);
        Category category = categoryMapper.convertToEntity(categoryDTO);
        Category createCategory = categoryRepository.save(category);
        log.info("카테고리 생성 완료: id={}, name={}", createCategory.getCategoryId(), createCategory.getCategoryName());
        return categoryMapper.convertToDTO(createCategory);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        categoryDTO.setCategoryLevel(fromJson(categoryDTO.getCategoryLevel()));
        categoryValidation.validateCategoryLevel(categoryDTO);
        categoryValidation.validateParentCategoryExistence(categoryDTO);
        categoryValidation.validateCategoryExistence(categoryId);

        Category category = categoryMapper.convertToEntity(categoryDTO);
        Category createCategory = categoryRepository.save(category);
        log.info("카테고리 수정 완료: id={}, name={}", createCategory.getCategoryId(), createCategory.getCategoryName());
        return categoryMapper.convertToDTO(createCategory);
    }

    @Override
    public Boolean deletedCategory(Long categoryId) {
        categoryValidation.validateCategoryExistence(categoryId);
        categoryValidation.validateCategoryHasChildren(categoryId);
        // 삭제시 예외 발생시 ContollerAvice을 통해 예외처리
        categoryRepository.deleteById(categoryId);
        return true;
    }

    public static String fromJson(String level) {
        String normalizedLevel = level.toUpperCase().replaceFirst("^LEVEL_", "");

        return Arrays.stream(CategoryLevel.values())
                .map(Enum::name)
                .filter(name -> name.equals("LEVEL_" + normalizedLevel))
                .findFirst()
                .orElseThrow(InvalidCategoryLevelException::new);
    }

}
