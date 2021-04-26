package com.company.ksena.entity.task;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|name")
@Table(name = "KSENA_KIND_OF_CLEANING")
@Entity(name = "ksena_KindOfCleaning")
public class KindOfCleaning extends StandardEntity {
    private static final long serialVersionUID = -9168510992003839719L;

    @Column(name = "NAME")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}