package com.company.ksena.entity.task;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Table(name = "KSENA_DAY_INTERVAL")
@Entity(name = "ksena_DayInterval")
public class DayInterval extends StandardEntity {
    private static final long serialVersionUID = 3905313110991798395L;

    @Min(1)
    @Max(7)
    @Column(name = "NUMBER_DAY")
    protected Integer numberDay;

    @Column(name = "NAME_DAY")
    protected String nameDay;

    public void setNameDay(DayOfWeek nameDay) {
        this.nameDay = nameDay == null ? null : nameDay.getId();
    }

    public DayOfWeek getNameDay() {
        return nameDay == null ? null : DayOfWeek.fromId(nameDay);
    }

    public void setNumberDay(Integer numberDay) {
        this.numberDay = numberDay;
    }

    public Integer getNumberDay() {
        return numberDay;
    }

}