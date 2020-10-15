package com.company.ksena.entity.template;

import com.company.ksena.entity.cleaning_map.PositionWrapper;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "KSENA_TEMPLATE")
@Entity(name = "ksena_Template")
@NamePattern("%s|name")
public class Template extends StandardEntity {
    private static final long serialVersionUID = 4850548988473243398L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DATE_OF_CREATION")
    private LocalDateTime dateOfCreation = LocalDateTime.now();

    @Column(name = "DATE_OF_UPDATE")
    private LocalDateTime dateOfUpdate = LocalDateTime.now();

    @OneToMany(mappedBy = "template")
    private List<PositionWrapper> cleaningMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<PositionWrapper> getCleaningMap() {
        return cleaningMap;
    }

    public void setCleaningMap(List<PositionWrapper> cleaningMap) {
        this.cleaningMap = cleaningMap;
    }

    public LocalDateTime getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(LocalDateTime dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }
}