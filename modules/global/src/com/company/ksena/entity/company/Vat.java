package com.company.ksena.entity.company;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Vat implements EnumClass<Integer> {

    TEN_PERSENT(10),
    FIFTEEN_PERCENT(15),
    TWENTY_ONE_PERCENT(21);

    private Integer id;

    Vat(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static Vat fromId(Integer id) {
        for (Vat at : Vat.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}