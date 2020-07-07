package com.company.ksena.entity.task;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.people.ClientEmployee;
import com.company.ksena.entity.people.Employee;
import com.company.ksena.entity.point.Point;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@PublishEntityChangedEvents
@Table(name = "KSENA_TASK")
@Entity(name = "ksena_Task")
public class Task extends StandardEntity {
    private static final long serialVersionUID = -2730506669836310740L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_DOCUMENT_ID")
    protected TaskDocument taskDocument;

    @Column(name = "DATE_OF_COMPLETION")
    protected LocalDate dateOfCompletion;

    @Column(name = "TASK_STATUS")
    protected String taskStatus;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_ID")
    protected Point point;

    @JoinTable(name = "KSENA_TASK_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    @ManyToMany
    protected List<Employee> employees;

    @JoinTable(name = "KSENA_TASK_CLEANING_POSITION_LINK",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "CLEANING_POSITION_ID"))
    @ManyToMany
    protected List<CleaningPosition> cleaningMap;

    @JoinTable(name = "KSENA_TASK_INVENTORY_LINK",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "INVENTORY_ID"))
    @ManyToMany
    protected List<Inventory> inventory;

    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

    public List<CleaningPosition> getCleaningMap() {
        return cleaningMap;
    }

    public void setCleaningMap(List<CleaningPosition> cleaningMap) {
        this.cleaningMap = cleaningMap;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus == null ? null : TaskStatus.fromId(taskStatus);
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.getId();
    }

    public LocalDate getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(LocalDate dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public TaskDocument getTaskDocument() {
        return taskDocument;
    }

    public void setTaskDocument(TaskDocument taskDocument) {
        this.taskDocument = taskDocument;
    }
}