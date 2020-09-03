package com.company.ksena.entity.task;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.point.Point;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@PublishEntityChangedEvents
@NamePattern("%s|docNumber")
@Table(name = "KSENA_TASK_DOCUMENT")
@Entity(name = "ksena_TaskDocument")
public class TaskDocument extends StandardEntity {
    private static final long serialVersionUID = 2314314178317765350L;

    @NotNull
    @Column(name = "DOC_NUMBER", unique = true)
    protected String docNumber;

    @Column(name = "CREATE_DATE")
    protected LocalDate createDate;

    @Column(name = "DATE_OF_COMPLETION")
    protected LocalDate dateOfCompletion;

    @Column(name = "COST_PER_HOUR")
    protected Double costPerHour;

    @Column(name = "FULL_COST")
    protected Double fullCost;

    @Column(name = "TYPE_OF_COST_FORMATION")
    protected String typeOfCostFormation;

    @Column(name = "IS_ACTIVE")
    protected Boolean isActive;

    @Column(name = "TASK_TYPE")
    protected String taskType;

    @Column(name = "DELAY")
    protected Integer delay;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_ID")
    protected Point point;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    protected Company company;

    @Column(name = "TYPE_OF_PERIODICITY")
    protected String typeOfPeriodicity;

    @Column(name = "INTERVAL")
    protected Integer periodicity;

    @JoinTable(name = "KSENA_TASK_DOCUMENT_INVENTORY_LINK",
            joinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "INVENTORY_ID"))
    @ManyToMany
    protected List<Inventory> inventory;

    @JoinTable(name = "KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK",
            joinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "DAY_INTERVAL_ID"))
    @ManyToMany
    protected List<DayInterval> cleaningDay;

    @JoinTable(name = "KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK", joinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"), inverseJoinColumns = @JoinColumn(name = "CLEANING_POSITION_ID"))
    @ManyToMany
    protected List<CleaningPosition> cleaningMap;

    @OneToMany(mappedBy = "taskDocument")
    protected List<Task> task;

    public Double getFullCost() {
        return fullCost;
    }

    public void setFullCost(Double fullCost) {
        this.fullCost = fullCost;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Task> getTask() {
        return task;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }

    public List<DayInterval> getCleaningDay() {
        return cleaningDay;
    }

    public void setCleaningDay(List<DayInterval> cleaningDay) {
        this.cleaningDay = cleaningDay;
    }

    public Integer getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Integer periodicity) {
        this.periodicity = periodicity;
    }

    public TypeOfPeriodicity getTypeOfPeriodicity() {
        return typeOfPeriodicity == null ? null : TypeOfPeriodicity.fromId(typeOfPeriodicity);
    }

    public void setTypeOfPeriodicity(TypeOfPeriodicity typeOfPeriodicity) {
        this.typeOfPeriodicity = typeOfPeriodicity == null ? null : typeOfPeriodicity.getId();
    }

    public Double getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(Double costPerHour) {
        this.costPerHour = costPerHour;
    }

    public TypeOfCostFormation getTypeOfCostFormation() {
        return typeOfCostFormation == null ? null : TypeOfCostFormation.fromId(typeOfCostFormation);
    }

    public void setTypeOfCostFormation(TypeOfCostFormation typeOfCostFormation) {
        this.typeOfCostFormation = typeOfCostFormation == null ? null : typeOfCostFormation.getId();
    }

    public LocalDate getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(LocalDate dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public void setCleaningMap(List<CleaningPosition> cleaningMap) {
        this.cleaningMap = cleaningMap;
    }

    public List<CleaningPosition> getCleaningMap() {
        return cleaningMap;
    }

    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public TaskType getTaskType() {
        return taskType == null ? null : TaskType.fromId(taskType);
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType == null ? null : taskType.getId();
    }

    public LocalDate getCreateDate() {
              return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        if (createDate == null) {
            this.createDate = LocalDate.now();
        }
        else{ this.createDate = createDate;
        }

    }

}