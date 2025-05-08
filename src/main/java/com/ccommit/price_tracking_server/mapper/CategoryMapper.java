package com.ccommit.price_tracking_server.mapper;

import com.ccommit.price_tracking_server.DTO.CategoryDTO;
import com.ccommit.price_tracking_server.entity.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public Category convertToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public CategoryDTO convertToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category updateEntity(Category category, CategoryDTO categoryDTO) {
        ModelMapper tempMapper = new ModelMapper(); // 해당 메서드에서만 사용할 새로운 ModelMapper 인스턴스 생성
        tempMapper.getConfiguration().setSkipNullEnabled(true); // null 값은 무시
        tempMapper.map(categoryDTO, category);
        return category;
    }
}
