package com.ccommit.price_tracking_server.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

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


    @JsonValue
    public String getCategoryLevelString() {
        return name().toLowerCase();
    }
}
