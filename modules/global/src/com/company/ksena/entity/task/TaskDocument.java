package com.company.ksena.entity.task;

import com.company.ksena.entity.cleaning_map.PositionWrapper;
import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.inventory.InventoryWrapper;
import com.company.ksena.entity.people.Employee;
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

    @Column(name = "ADD_PRISE_EXPENDABLE_MATERIAL")
    protected Boolean addPriseExpendableMaterial;

    @NotNull
    @Column(name = "CREATE_DATE")
    protected LocalDate createDate;

    @NotNull
    @Column(name = "DATE_OF_COMPLETION")
    protected LocalDate dateOfCompletion;

    @NotNull
    @Column(name = "DATE_OF_END_DOCUMENT")
    protected LocalDate dateOfEndDocument;

    @Column(name = "COST_PER_HOUR")
    protected Double costPerHour;

    @Column(name = "FULL_COST")
    protected Double fullCost;


    @Column(name = "SALARY_ELEMENTARY")
    protected Long salaryElementary;

    @Column(name = "SALARY_MEDIUM")
    protected Long salaryMedium;

    @Column(name = "SALARY_HIGH")
    protected Long salaryHigh;


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

    @JoinTable(name = "KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK",
            joinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "DAY_INTERVAL_ID"))
    @ManyToMany
    protected List<DayInterval> cleaningDay;

    @OneToMany(mappedBy = "taskDocument")
    protected List<Task> task;

    @OneToMany(mappedBy = "taskDocuments")
    protected List<PositionWrapper> cleaningMap;

    @OneToMany(mappedBy = "taskDocuments")
    protected List<InventoryWrapper> inventoryMap;

    @JoinTable(name = "KSENA_TASK_DOCUMENT_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    @ManyToMany
    protected List<Employee> employeesMap;

    public LocalDate getDateOfEndDocument() {
        return dateOfEndDocument;
    }

    public void setDateOfEndDocument(LocalDate dateOfEndDocument) {
        this.dateOfEndDocument = dateOfEndDocument;
    }

    public List<Employee> getEmployeesMap() {
        return employeesMap;
    }

    public void setEmployeesMap(List<Employee> employeesMap) {
        this.employeesMap = employeesMap;
    }

    public void setInventoryMap(List<InventoryWrapper> inventoryMap) {
        this.inventoryMap = inventoryMap;
    }

    public List<InventoryWrapper> getInventoryMap() {
        return inventoryMap;
    }

    public List<PositionWrapper> getCleaningMap() {
        return cleaningMap;
    }

    public void setCleaningMap(List<PositionWrapper> cleaningMap) {
        this.cleaningMap = cleaningMap;
    }

    public Boolean getAddPriseExpendableMaterial() {
        return addPriseExpendableMaterial;
    }

    public void setAddPriseExpendableMaterial(Boolean addPriseExpendableMaterial) {
        this.addPriseExpendableMaterial = addPriseExpendableMaterial;
    }

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