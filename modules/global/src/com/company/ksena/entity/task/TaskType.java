package com.company.ksena.entity.task;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TaskType implements EnumClass<String> {

    ONE_TIME("ONE TIME"),
    REPEAT("REPEAT");

    private String id;

    TaskType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TaskType fromId(String id) {
        for (TaskType at : TaskType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}