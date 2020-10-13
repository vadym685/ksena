package com.company.ksena.entity.cleaning_map;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "KSENA_ROOM")
@Entity(name = "ksena_Room")
public class Room extends StandardEntity {
    private static final long serialVersionUID = 1054035765894993876L;

    @Column(name = "NAME")
    protected String name;

    @OneToMany(mappedBy = "roomName")
    protected List<PositionWrapper> positionWrappers;

    @Column(name = "COMENT")
    protected String coment;


    @Column(name = "COLOR")
    protected String color;

    public List<PositionWrapper> getPositionWrappers() {
        return positionWrappers;
    }

    public void setPositionWrappers(List<PositionWrapper> positionWrappers) {
        this.positionWrappers = positionWrappers;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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