package com.ccommit.price_tracking_server.DTO;

import com.ccommit.price_tracking_server.enums.CategoryLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @Size(max = 20)
    @NotBlank
    private String categoryName;

    @NotNull
    private CategoryLevel categoryLevel;

    private Long parentCategoryId;

}
