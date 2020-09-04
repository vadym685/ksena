package com.company.ksena.entity.task;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.inventory.Inventory;
import com.company.ksena.entity.people.Employee;
import com.company.ksena.entity.point.Point;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NamePattern("%s|taskNumber")
@PublishEntityChangedEvents
@Table(name = "KSENA_TASK")
@Entity(name = "ksena_Task")
public class Task extends StandardEntity {
    private static final long serialVersionUID = -2730506669836310740L;

    @Column(name = "TASK_NUMBER", unique = true)
    protected String taskNumber;

    @Column(name = "ADD_PRISE_EXPENDABLE_MATERIAL")
    protected Boolean addPriseExpendableMaterial;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_DOCUMENT_ID")
    protected TaskDocument taskDocument;

    @Lookup(type = LookupType.DROPDOWN, actions = {"open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    protected Company company;

    @Lookup(type = LookupType.DROPDOWN, actions = {"open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_ID")
    protected Point point;

    @Positive
    @Column(name = "COST")
    protected Double cost;

    @Column(name = "DEALY")
    protected Integer delay;

    @Column(name = "TASK_TIME_PLANE")
    protected LocalTime taskTimePlane;

    @Column(name = "TASK_TIME_FACTUAL")
    protected LocalTime taskTimeFactual;

    @Column(name = "DATE_OF_COMPLETION")
    protected LocalDate dateOfCompletion;

    @Column(name = "TASK_STATUS")
    protected String taskStatus;

    @Column(name = "SALARY_ELEMENTARY")
    protected Long salaryElementary;

    @Column(name = "SALARY_MEDIUM")
    protected Long salaryMedium;

    @Column(name = "SALARY_HIGH")
    protected Long salaryHigh;

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

    public Boolean getAddPriseExpendableMaterial() {
        return addPriseExpendableMaterial;
    }

    public void setAddPriseExpendableMaterial(Boolean addPriseExpendableMaterial) {
        this.addPriseExpendableMaterial = addPriseExpendableMaterial;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Long getSalaryElementary() {
        return salaryElementary;
    }

    public void setSalaryElementary(Long salaryElementary) {
        this.salaryElementary = salaryElementary;
    }

    public Long getSalaryMedium() {
        return salaryMedium;
    }

    public void setSalaryMedium(Long salaryMedium) {
        this.salaryMedium = salaryMedium;
    }

    public Long getSalaryHigh() {
        return salaryHigh;
    }

    public void setSalaryHigh(Long salaryHigh) {
        this.salaryHigh = salaryHigh;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Company getCompany() {
        return company;
    }

    public LocalTime getTaskTimeFactual() {
        return taskTimeFactual;
    }

    public void setTaskTimeFactual(LocalTime taskTimeFactual) {
        this.taskTimeFactual = taskTimeFactual;
    }

    public LocalTime getTaskTimePlane() {
        return taskTimePlane;
    }

    public void setTaskTimePlane(LocalTime taskTimePlane) {
        this.taskTimePlane = taskTimePlane;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

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