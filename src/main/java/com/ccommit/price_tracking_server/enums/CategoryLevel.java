package com.ccommit.price_tracking_server.enums;

import com.ccommit.price_tracking_server.exception.InvalidCategoryLevelException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CategoryLevel {
    LEVEL_1(1),
    LEVEL_2(2),
    LEVEL_3(3);

    private final int level;

    CategoryLevel(int level) {
        this.level = level;
    }

    public static int getMaxLevel() {
        return values().length;
    }

    @JsonCreator
    public static CategoryLevel fromJson(int level) {
        return Arrays.stream(CategoryLevel.values())
                .filter(e -> e.getLevel() == level)
                .findFirst()
                .orElseThrow(InvalidCategoryLevelException::new);
    }

    @JsonValue
    public String getCategoryLevelString() {
        return name().toLowerCase();
    }
}
