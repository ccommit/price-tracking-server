package com.ccommit.price_tracking_server.service;

import com.ccommit.price_tracking_server.DTO.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

    Boolean deletedCategory(Long categoryId);
}
