package com.demo.DressMeUp.domain.cloth.domain;

public enum ClothType {
    TOP,
    DRESS,
    BOTTOM;

    public static ClothType fromType(String type) {
        switch (type) {
            case "TOP":
                return TOP;
            case "DRESS":
                return DRESS;
            case "BOTTOM":
                return BOTTOM;
        }
        return null;
    }
}
