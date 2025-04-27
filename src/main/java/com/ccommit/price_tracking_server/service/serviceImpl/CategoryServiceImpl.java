package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.DTO.CategoryDTO;
import com.ccommit.price_tracking_server.entity.Category;
import com.ccommit.price_tracking_server.exception.CategoryHasChildrenException;
import com.ccommit.price_tracking_server.exception.CategoryNotFoundException;
import com.ccommit.price_tracking_server.exception.InvalidCategoryLevelException;
import com.ccommit.price_tracking_server.exception.ParentCategoryNotFoundException;
import com.ccommit.price_tracking_server.mapper.CategoryMapper;
import com.ccommit.price_tracking_server.repository.CategoryRepository;
import com.ccommit.price_tracking_server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getCategoryLevel() == null) {
            log.error("해당 카테고리 레벨은 없는 레벨입니다.");
            throw new InvalidCategoryLevelException();
        }

        if (categoryDTO.getParentCategoryId() != null &&
                !categoryRepository.existsById(categoryDTO.getParentCategoryId())) {
            log.error("해당 부모 카테고리가 존재하지 않습니다: parentCategoryId={}", categoryDTO.getParentCategoryId());
            throw new ParentCategoryNotFoundException();
        }
        Category category = categoryMapper.convertToEntity(categoryDTO);
        Category createCategory = categoryRepository.save(category);
        log.info("카테고리 생성 완료: id={}, name={}", createCategory.getCategoryId(), createCategory.getCategoryName());
        return categoryMapper.convertToDTO(createCategory);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        if (categoryDTO.getCategoryLevel() == null) {
            log.error("해당 카테고리 레벨은 없는 레벨입니다.");
            throw new InvalidCategoryLevelException();
        }

        if (categoryDTO.getParentCategoryId() != null &&
                !categoryRepository.existsById(categoryDTO.getParentCategoryId())) {
            log.error("해당 부모 카테고리가 존재하지 않습니다: parentCategoryId={}", categoryDTO.getParentCategoryId());
            throw new ParentCategoryNotFoundException();
        }

        if (!categoryRepository.existsById(categoryId)) {
            log.error("해당 카테고리가 존재하지 않습니다: categoryId={}", categoryId);
            throw new CategoryNotFoundException();
        }

        Category category = categoryMapper.convertToEntity(categoryDTO);
        Category createCategory = categoryRepository.save(category);
        log.info("카테고리 수정 완료: id={}, name={}", createCategory.getCategoryId(), createCategory.getCategoryName());
        return categoryMapper.convertToDTO(createCategory);
    }

    @Override
    public Boolean deletedCategory(Long categoryId) {
        if(!categoryRepository.existsById(categoryId)){
            log.error("해당 카테고리가 존재하지 않습니다: categoryId={}", categoryId);
            throw new CategoryNotFoundException();
        }
        if(categoryRepository.existsByParentCategory_CategoryId(categoryId)){
            log.error("해당 카테고리의 하위 카테고리가 존재합니다: categoryId={}", categoryId);
            throw new CategoryHasChildrenException();
        }
        // 삭제시 예외 발생시 ContollerAvice을 통해 예외처리
        categoryRepository.deleteById(categoryId);
        return true;
    }

}
