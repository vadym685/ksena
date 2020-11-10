package com.company.ksena.entity.people;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ResidenceType implements EnumClass<String> {

    CZECH_CITIZENSHIP("Czech citizenship"),
    PERMANENT_RESIDENCED("Permanent residence"),
    FAMILY_REUNION("Family reunion"),
    EU_PASSPORT("EU passport"),
    WORK_VISA("Work visa"),
    STUDENT_VISA("Student visa"),
    ASYLUM("Asylum"),
    TEMPORARY_VISA("Temporary visa"),
    OTHER("Other");

    private String id;

    ResidenceType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ResidenceType fromId(String id) {
        for (ResidenceType at : ResidenceType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}