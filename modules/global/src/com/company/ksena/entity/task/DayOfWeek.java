package com.company.ksena.entity.task;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DayOfWeek implements EnumClass<String> {

    MONDAY("MONDAY"),
    TUESDAY("TUESDAY"),
    WEDNESDAY("WEDNESDAY"),
    THURSDAY("THURSDAY"),
    FRIDAY("FRIDAY"),
    SATURDAY("SATURDAY"),
    SUNDAY("SUNDAY");

    private String id;

    DayOfWeek(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DayOfWeek fromId(String id) {
        for (DayOfWeek at : DayOfWeek.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}