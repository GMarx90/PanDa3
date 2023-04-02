package com.example.panda3.utils;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;


public enum FuelTypeEnum {
    GASOLINE, LPG, DIESEL;

    public static FuelTypeEnum valueOfLabel(String label) {
        for (FuelTypeEnum e : values()) {
            if (e.name().equals(label)) {
                return e;
            }
        }
        return null;
    }
}
