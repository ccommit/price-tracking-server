package com.ccommit.price_tracking_server.DTO;

import com.ccommit.price_tracking_server.validation.anotaion.ParentCategoryExists;
import com.ccommit.price_tracking_server.validation.anotaion.ValidCategoryLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @Size(max = 20)
    @NotBlank
    private String categoryName;

    @NotNull
    @ValidCategoryLevel
    @Setter
    private String categoryLevel;

    @ParentCategoryExists
    private Long parentCategoryId;

}
