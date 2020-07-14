package com.company.ksena.entity.people;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Qualification implements EnumClass<String> {

    ELEMENTARY("ELEMENTARY"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private String id;

    Qualification(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Qualification fromId(String id) {
        for (Qualification at : Qualification.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}