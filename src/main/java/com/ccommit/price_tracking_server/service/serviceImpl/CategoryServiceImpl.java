package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.DTO.CategoryDTO;
import com.ccommit.price_tracking_server.entity.Category;
import com.ccommit.price_tracking_server.enums.CategoryLevel;
import com.ccommit.price_tracking_server.exception.CategoryHasChildrenException;
import com.ccommit.price_tracking_server.exception.InvalidCategoryLevelException;
import com.ccommit.price_tracking_server.mapper.CategoryMapper;
import com.ccommit.price_tracking_server.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        categoryDTO.setCategoryLevel(fromJson(categoryDTO.getCategoryLevel()));
        Category category = categoryMapper.convertToEntity(categoryDTO);
        Category createCategory = categoryRepository.save(category);
        log.info("카테고리 생성 완료: id={}, name={}", createCategory.getCategoryId(), createCategory.getCategoryName());
        return categoryMapper.convertToDTO(createCategory);
    }


    @Transactional
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        categoryDTO.setCategoryLevel(fromJson(categoryDTO.getCategoryLevel()));
        Category categoryById = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다: id=" + categoryId));
        categoryById = categoryMapper.updateEntity(categoryById, categoryDTO);
        Category createCategory = categoryRepository.save(categoryById);
        log.info("카테고리 수정 완료: id={}, name={}", createCategory.getCategoryId(), createCategory.getCategoryName());
        return categoryMapper.convertToDTO(createCategory);
    }

    @Transactional
    public Boolean deletedCategory(Long categoryId) {
        if (categoryRepository.existsByParentCategory_CategoryId(categoryId)) {
            log.error("해당 카테고리의 하위 카테고리가 존재합니다: categoryId={}", categoryId);
            throw new CategoryHasChildrenException();
        }
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
