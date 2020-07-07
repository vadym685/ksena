package com.company.ksena.entity.task;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TaskStatus implements EnumClass<String> {

    CREATE("CREATE"),
    SHEDULED("SHEDULED"),
    EXECUTED("EXECUTED"),
    UNCOMPLETED("UNCOMPLETED"),
    REJECTED("REJECTED");

    private String id;

    TaskStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TaskStatus fromId(String id) {
        for (TaskStatus at : TaskStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}