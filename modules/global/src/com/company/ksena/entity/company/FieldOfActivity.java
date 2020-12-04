package com.company.ksena.entity.company;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|name")
@Table(name = "KSENA_FIELD_OF_ACTIVITY")
@Entity(name = "ksena_FieldOfActivity")
public class FieldOfActivity extends StandardEntity {
    private static final long serialVersionUID = 3834949123702483908L;

    @Column(name = "NAME")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}