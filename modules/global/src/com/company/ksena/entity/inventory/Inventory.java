package com.company.ksena.entity.inventory;

import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskDocument;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Table(name = "KSENA_INVENTORY")
@Entity(name = "ksena_Inventory")
@NamePattern("%s|name")
public class Inventory extends StandardEntity {
    private static final long serialVersionUID = -4224745053148550858L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "SERIAL_NUMBER")
    protected String serialNumber;

    @Column(name = "COLOUR")
    protected String colour;

    @Column(name = "COMMISSIONING_DATE")
    protected LocalDate commissioningDate;

    @Column(name = "DECOMMISSIONING_DATE")
    protected LocalDate decommissioningDate;

    @Column(name = "AVAILABLE_FOR_USE")
    protected Boolean availableForUse;

    @Column(name = "REASON_FOR_DECOMMISSIONING")
    protected String reasonForDecommissioning;

    @Column(name = "QUANTITY_INVENTORY")
    protected Integer quantityInventory;

    @Column(name = "NOTE_INVENTORY")
    protected String noteInventory;

    @Column(name = "VISIBLE")
    protected Boolean visible;

    @JoinTable(name = "KSENA_TASK_INVENTORY_LINK",
            joinColumns = @JoinColumn(name = "INVENTORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "TASK_ID"))
    @ManyToMany
    protected List<Task> tasks;

    @JoinTable(name = "KSENA_TASK_DOCUMENT_INVENTORY_LINK",
            joinColumns = @JoinColumn(name = "INVENTORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"))
    @ManyToMany
    protected List<TaskDocument> taskDocuments;

    @OneToMany(mappedBy = "inventory")
    protected List<Breakage> breakage;

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean Visible) {
        this.visible = Visible;
    }

    public String getNoteInventory() {
        return noteInventory;
    }

    public void setNoteInventory(String noteInventory) {
        this.noteInventory = noteInventory;
    }

    public Integer getQuantityInventory() {
        return quantityInventory;
    }

    public void setQuantityInventory(Integer quantityInventory) {
        this.quantityInventory = quantityInventory;
    }

    public void setBreakage(List<Breakage> breakage) {
        this.breakage = breakage;
    }

    public List<Breakage> getBreakage() {
        return breakage;
    }

    public String getReasonForDecommissioning() {
        return reasonForDecommissioning;
    }

    public void setReasonForDecommissioning(String reasonForDecommissioning) {
        this.reasonForDecommissioning = reasonForDecommissioning;
    }

    public Boolean getAvailableForUse() {
        return availableForUse;
    }

    public void setAvailableForUse(Boolean availableForUse) {
        this.availableForUse = availableForUse;
    }

    public LocalDate getDecommissioningDate() {
        return decommissioningDate;
    }

    public void setDecommissioningDate(LocalDate decommissioningDate) {
        this.decommissioningDate = decommissioningDate;
    }

    public LocalDate getCommissioningDate() {
        return commissioningDate;
    }

    public void setCommissioningDate(LocalDate commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<TaskDocument> getTaskDocuments() {
        return taskDocuments;
    }

    public void setTaskDocuments(List<TaskDocument> taskDocuments) {
        this.taskDocuments = taskDocuments;
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