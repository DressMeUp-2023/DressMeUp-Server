package com.demo.DressMeUp.domain.cloth.domain;

public enum ClothType {
    TOP,
    L_SKIRT,
    S_SKIRT,
    DRESS,
    SHORT,
    TROUSER;

    public static ClothType fromType(String type) {
        switch (type) {
            case "TOP":
                return TOP;
            case "DRESS":
                return DRESS;
            case "L_SKIRT":
                return L_SKIRT;
            case "S_SKIRT":
                return S_SKIRT;
            case "SHORT":
                return SHORT;
            case "TROUSER":
                return TROUSER;
        }
        return null;
    }
}
