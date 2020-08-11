package com.company.ksena.entity.inventory;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.NumberFormat;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "KSENA_EXPENDABLE_MATERIAL")
@Entity(name = "ksena_ExpendableMaterial")
public class ExpendableMaterial extends StandardEntity {
    private static final long serialVersionUID = 3769957543236503392L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "STANDART_AMOUNT")
    protected Double standartAmount;

    @NumberFormat(pattern = "##,##0.00")
    @Positive
    @Column(name = "PRICE")
    protected Double price;

    @JoinTable(name = "KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK",
            joinColumns = @JoinColumn(name = "EXPENDABLE_MATERIAL_ID"),
            inverseJoinColumns = @JoinColumn(name = "CLEANING_POSITION_ID"))
    @ManyToMany
    protected List<CleaningPosition> cleaningPositions;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<CleaningPosition> getCleaningPositions() {
        return cleaningPositions;
    }

    public void setCleaningPositions(List<CleaningPosition> cleaningPositions) {
        this.cleaningPositions = cleaningPositions;
    }

    public Double getStandartAmount() {
        return standartAmount;
    }

    public void setStandartAmount(Double standartAmount) {
        this.standartAmount = standartAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}