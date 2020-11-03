package com.company.ksena.entity.company;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|name")
@Table(name = "KSENA_COMPANY_CATEGORY")
@Entity(name = "ksena_CompanyCategory")
public class CompanyCategory extends StandardEntity {
    private static final long serialVersionUID = 4350482284028614764L;

    @Column(name = "NAME")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}