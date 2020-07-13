package com.company.ksena.entity.inventory;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.time.LocalDate;

@NamePattern("%s|description")
@Table(name = "KSENA_BREAKAGE")
@Entity(name = "ksena_Breakage")
public class Breakage extends StandardEntity {
    private static final long serialVersionUID = -4052638627113499548L;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "BREAKDOWN_DATE")
    protected LocalDate breakdownDate;

    @Column(name = "FIX_DATE")
    protected LocalDate fixDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVENTORY_ID")
    protected Inventory inventory;

    public LocalDate getFixDate() {
        return fixDate;
    }

    public void setFixDate(LocalDate fixDate) {
        this.fixDate = fixDate;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public LocalDate getBreakdownDate() {
        return breakdownDate;
    }

    public void setBreakdownDate(LocalDate breakdownDate) {
        this.breakdownDate = breakdownDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}