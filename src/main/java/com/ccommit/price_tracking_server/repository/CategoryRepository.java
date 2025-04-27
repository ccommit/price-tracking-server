package com.ccommit.price_tracking_server.repository;

import com.ccommit.price_tracking_server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    Boolean existsByParentCategory_CategoryId(Long parentCategoryId);
}
