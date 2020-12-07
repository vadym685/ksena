package com.company.ksena.entity.task;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TypeOfCostFormation implements EnumClass<String> {

    FOR_TIME("FOR TIME"),
    FIXED_PRICE("FIXED PRICE "),
    FIXED_PRICE_FOR_CLEANING("FIXED PRICE FOR CLEANING");

    private String id;

    TypeOfCostFormation(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TypeOfCostFormation fromId(String id) {
        for (TypeOfCostFormation at : TypeOfCostFormation.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}