package com.company.ksena.entity.company;

import com.company.ksena.entity.people.Sex;
import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum BillSendType implements EnumClass<String> {

    EMAIL, MAIL, ORIGINAL;

    @Override
    public String getId() {
        return this.name();
    }

    @Nullable
    public static BillSendType fromId(String id) {
        for (BillSendType at : BillSendType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
