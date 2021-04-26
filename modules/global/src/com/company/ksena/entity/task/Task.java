package com.company.ksena.entity.task;

import com.company.ksena.entity.cleaning_map.PositionWrapper;
import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.inventory.InventoryWrapper;
import com.company.ksena.entity.people.Employee;
import com.company.ksena.entity.point.Point;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NamePattern("%s|taskNumber")
@PublishEntityChangedEvents
@Table(name = "KSENA_TASK")
@Entity(name = "ksena_Task")
public class Task extends StandardEntity {
    private static final long serialVersionUID = -2730506669836310740L;

    @NotNull
    @Column(name = "TASK_NUMBER", unique = true)
    protected String taskNumber;

    @Column(name = "INVENTORY_DELIVERY_REQUIRED")
    protected Boolean inventoryDeliveryRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KIND_OF_CLEANING_ID")
    protected KindOfCleaning kindOfCleaning;

    @Column(name = "COST_OF_DELIVERY")
    protected Double costOfDelivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESPONSIBLE_FOR_THE_DELIVERY_OF_INVENTORY_ID")
    protected Employee responsibleForTheDeliveryOfInventory;

    @Column(name = "TIME_FOR_START_COMPLETION")
    protected LocalTime timeForStartCompletion;

    @Column(name = "COMMENT")
    protected String comment;

    @Column(name = "CLIENT_COMMENT")
    protected String clientComment;

    @Column(name = "PLANNED_CLEANING_VOLUME")
    protected Double plannedCleaningVolume;

    @Column(name = "ACTUAL_CLEANING_VOLUME")
    protected Double factualCleaningVolume;

    @Column(name = "FACTUAL_CLEANING_COST")
    protected Double factualCleaningCost;

    @Column(name = "PLANNED_CLEANING_COST")
    protected Double plannedCleaningCost;

    @Column(name = "ADD_PRISE_EXPENDABLE_MATERIAL")
    protected Boolean addPriseExpendableMaterial;

    @Column(name = "PRISE_EXPENDABLE_MATERIAL")
    protected Double priсeExpendableMaterial;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_DOCUMENT_ID")
    protected TaskDocument taskDocument;

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

    @Positive
    @Column(name = "SALARY_ELEMENTARY")
    protected Long salaryElementary;

    @Positive
    @Column(name = "SALARY_MEDIUM")
    protected Long salaryMedium;

    @Positive
    @Column(name = "SALARY_HIGH")
    protected Long salaryHigh;

    @JoinTable(name = "KSENA_TASK_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    @ManyToMany
    protected List<Employee> employees;

    @OneToMany(mappedBy = "task")
    protected List<PositionWrapper> cleaningMap = new ArrayList<PositionWrapper>();

    @OneToMany(mappedBy = "task")
    protected List<GoogleCalendarEventId> googleCalendarEventId;

    @OneToMany(mappedBy = "task")
    protected List<InventoryWrapper> inventoryMap = new ArrayList<InventoryWrapper>();

    @Column(name = "TYPE_OF_COST_FORMATION")
    protected String typeOfCostFormation;

    @Positive
    @Column(name = "FIXED_COST_FOR_CLEANING")
    protected Double fixedCostForCleaning;

    @Positive
    @Column(name = "FULL_COST")
    protected Double fullCost;

    @Positive
    @Column(name = "COST_PER_HOUR")
    protected Double costPerHour;

    @Column(name = "ADDITIONAL_CUSTOMER_PAYMENT")
    private Double additionalCustomerPayment;

    @Column(name = "ADDITIONAL_EMPLOYEE_PAYMENT")
    private Double additionalEmployeePayment;

    @Column(name = "TRANSPORT_COSTS_CUSTOMER")
    private Double transportCostsCustomer;

    @Column(name = "TRANSPORT_COSTS_EMPLOYEE")
    private Double transportCostsEmployee;

    public void setFactualCleaningCost(Double factualCleaningCost) {
        this.factualCleaningCost = factualCleaningCost;
    }

    public Double getFactualCleaningCost() {
        return factualCleaningCost;
    }

    public Double getPlannedCleaningCost() {
        return plannedCleaningCost;
    }

    public void setPlannedCleaningCost(Double plannedCleaningCost) {
        this.plannedCleaningCost = plannedCleaningCost;
    }

    public Double getFactualCleaningVolume() {
        return factualCleaningVolume;
    }

    public void setFactualCleaningVolume(Double factualCleaningVolume) {
        this.factualCleaningVolume = factualCleaningVolume;
    }

    public Double getPlannedCleaningVolume() {
        return plannedCleaningVolume;
    }

    public void setPlannedCleaningVolume(Double plannedCleaningVolume) {
        this.plannedCleaningVolume = plannedCleaningVolume;
    }

    public String getClientComment() {
        return clientComment;
    }

    public void setClientComment(String clientComment) {
        this.clientComment = clientComment;
    }

    public Boolean getInventoryDeliveryRequired() {
        return inventoryDeliveryRequired;
    }

    public void setInventoryDeliveryRequired(Boolean inventoryDeliveryRequired) {
        this.inventoryDeliveryRequired = inventoryDeliveryRequired;
    }

    public Employee getResponsibleForTheDeliveryOfInventory() {
        return responsibleForTheDeliveryOfInventory;
    }

    public void setResponsibleForTheDeliveryOfInventory(Employee responsibleForTheDeliveryOfInventory) {
        this.responsibleForTheDeliveryOfInventory = responsibleForTheDeliveryOfInventory;
    }

    public Double getCostOfDelivery() {
        return costOfDelivery;
    }

    public void setCostOfDelivery(Double costOfDelivery) {
        this.costOfDelivery = costOfDelivery;
    }

    public LocalTime getTimeForStartCompletion() {
        return timeForStartCompletion;
    }

    public void setTimeForStartCompletion(LocalTime timeForStartCompletion) {
        this.timeForStartCompletion = timeForStartCompletion;
    }

    public KindOfCleaning getKindOfCleaning() {
        return kindOfCleaning;
    }

    public void setKindOfCleaning(KindOfCleaning kindOfCleaning) {
        this.kindOfCleaning = kindOfCleaning;
    }

    public void setTypeOfCostFormation(String typeOfCostFormation) {
        this.typeOfCostFormation = typeOfCostFormation;
    }

    public Double getAdditionalCustomerPayment() {
        return additionalCustomerPayment;
    }

    public void setAdditionalCustomerPayment(Double additionalCustomerPayment) {
        this.additionalCustomerPayment = additionalCustomerPayment;
    }

    public Double getAdditionalEmployeePayment() {
        return additionalEmployeePayment;
    }

    public void setAdditionalEmployeePayment(Double additionalEmployeePayment) {
        this.additionalEmployeePayment = additionalEmployeePayment;
    }

    public Double getPriсeExpendableMaterial() {
        return priсeExpendableMaterial;
    }

    public void setPriсeExpendableMaterial(Double priсeExpendableMaterial) {
        this.priсeExpendableMaterial = priсeExpendableMaterial;
    }

    public void setFixedCostForCleaning(Double fixedCostForCleaning) {
        this.fixedCostForCleaning = fixedCostForCleaning;
    }

    public Double getFixedCostForCleaning() {
        return fixedCostForCleaning;
    }

    public Double getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(Double costPerHour) {
        this.costPerHour = costPerHour;
    }

    public Double getFullCost() {
        return fullCost;
    }

    public void setFullCost(Double fullCost) {
        this.fullCost = fullCost;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<GoogleCalendarEventId> getGoogleCalendarEventId() {
        return googleCalendarEventId;
    }

    public void setGoogleCalendarEventId(List<GoogleCalendarEventId> googleCalendarEventId) {
        this.googleCalendarEventId = googleCalendarEventId;
    }

    public List<InventoryWrapper> getInventoryMap() {
        return inventoryMap;
    }

    public void setInventoryMap(List<InventoryWrapper> inventoryMap) {
        this.inventoryMap = inventoryMap;
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

    public TypeOfCostFormation getTypeOfCostFormation() {
        return typeOfCostFormation == null ? null : TypeOfCostFormation.fromId(typeOfCostFormation);
    }

    public void setTypeOfCostFormation(TypeOfCostFormation typeOfCostFormation) {
        this.typeOfCostFormation = typeOfCostFormation == null ? null : typeOfCostFormation.getId();
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

    public Double getTransportCostsCustomer() {
        return transportCostsCustomer;
    }

    public void setTransportCostsCustomer(Double transportCostsCustomer) {
        this.transportCostsCustomer = transportCostsCustomer;
    }

    public Double getTransportCostsEmployee() {
        return transportCostsEmployee;
    }

    public void setTransportCostsEmployee(Double transportCostsEmployee) {
        this.transportCostsEmployee = transportCostsEmployee;
    }
}