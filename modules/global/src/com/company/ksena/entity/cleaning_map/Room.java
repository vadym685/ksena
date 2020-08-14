package com.company.ksena.entity.cleaning_map;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@NamePattern("%s|name")
@Table(name = "KSENA_ROOM")
@Entity(name = "ksena_Room")
public class Room extends StandardEntity {
    private static final long serialVersionUID = 1054035765894993876L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "COMENT")
    protected String coment;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "room")
    protected CleaningPosition cleaningPosition;

    public CleaningPosition getCleaningPosition() {
        return cleaningPosition;
    }

    public void setCleaningPosition(CleaningPosition cleaningPosition) {
        this.cleaningPosition = cleaningPosition;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}