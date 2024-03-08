package com.demo.DressMeUp.domain.cloth.domain;

public enum ClothType {
    TOP,
    BOTTOM,
    DRESS,
    SHORT,
    TROUSER;

    public static ClothType fromType(String type) {
        switch (type) {
            case "TOP":
                return TOP;
            case "DRESS":
                return DRESS;
            case "SHORT":
                return SHORT;
            case "TROUSER":
                return TROUSER;
        }
        return null;
    }
}
