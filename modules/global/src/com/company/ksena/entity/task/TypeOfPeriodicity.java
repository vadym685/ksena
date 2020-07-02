package com.company.ksena.entity.task;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TypeOfPeriodicity implements EnumClass<String> {

    PERIOD("PERIOD"),
    PERIODICITY("PERIODICITY");

    private String id;

    TypeOfPeriodicity(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TypeOfPeriodicity fromId(String id) {
        for (TypeOfPeriodicity at : TypeOfPeriodicity.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}