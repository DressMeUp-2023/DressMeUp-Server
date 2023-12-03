package com.demo.DressMeUp.domain.user.domain;


public enum Gender {
    MALE,
    FEMALE;

    public static Gender fromName(String gender) {
        switch (gender) {
            case "MALE":
                return MALE;
            case "FEMALE":
                return FEMALE;
        }
        return null;
    }
}
