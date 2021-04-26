package com.company.ksena.entity.people;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum EmployeeType implements EnumClass<String> {

    CADRES("CADRES"),
    SELF_EMPLOYED("SELF EMPLOYED"),
    TEMPORARY_STAFF("TEMPORARY STAFF"),
    ADMINISTRATIVE_STAFF("ADMINISTRATIVE STAFF"),
    EMPLOYMENT_AGREEMENT("EMPLOYMENT AGREEMENT"),
    DRIVER("DRIVER");

    private String id;

    EmployeeType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EmployeeType fromId(String id) {
        for (EmployeeType at : EmployeeType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}