package com.company.ksena.entity.task;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

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
    @JoinTable(name = "KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK",
            joinColumns = @JoinColumn(name = "DAY_INTERVAL_ID"),
            inverseJoinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"))
    @ManyToMany
    protected List<TaskDocument> taskDocuments;

    public List<TaskDocument> getTaskDocuments() {
        return taskDocuments;
    }

    public void setTaskDocuments(List<TaskDocument> taskDocuments) {
        this.taskDocuments = taskDocuments;
    }

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