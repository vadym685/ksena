package com.company.ksena.entity.task;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.people.Client;
import com.company.ksena.entity.people.Employee;
import com.company.ksena.entity.point.Point;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@PublishEntityChangedEvents
@NamePattern("%s|id")
@Table(name = "KSENA_TASK_DOCUMENT")
@Entity(name = "ksena_TaskDocument")
public class TaskDocument extends StandardEntity {
    private static final long serialVersionUID = 2314314178317765350L;

    @Column(name = "CREATE_DATE")
    protected LocalDate createDate;

    @Column(name = "DATE_OF_COMPLETION")
    protected LocalDate dateOfCompletion;

    @Column(name = "COST_PER_HOUR")
    protected Double costPerHour;

    @Column(name = "TYPE_OF_COST_FORMATION")
    protected String typeOfCostFormation;

    @Column(name = "IS_ACTIVE")
    protected Boolean isActive;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    protected Client client;

    @Column(name = "TASK_TYPE")
    protected String taskType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_ID")
    protected Point point;

    @Column(name = "TYPE_OF_PERIODICITY")
    protected String typeOfPeriodicity;

    @JoinTable(name = "KSENA_TASK_DOCUMENT_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    @ManyToMany
    protected List<Employee> employees;

    @JoinTable(name = "KSENA_TASK_DOCUMENT_INVENTORY_LINK",
            joinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "INVENTORY_ID"))
    @ManyToMany
    protected List<Inventory> inventory;

    @JoinTable(name = "KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK", joinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"), inverseJoinColumns = @JoinColumn(name = "CLEANING_POSITION_ID"))
    @ManyToMany
    protected List<CleaningPosition> cleaningMap;

    @Column(name = "INTERVAL")
    protected Integer interval;

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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