package com.ccommit.price_tracking_server.repository;

import com.ccommit.price_tracking_server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    Boolean existsByParentCategory_CategoryId(Long parentCategoryId);

    @Query("SELECT c FROM Category c WHERE c.subCategories IS EMPTY")
    List<Category> findLeafCategories();
}
