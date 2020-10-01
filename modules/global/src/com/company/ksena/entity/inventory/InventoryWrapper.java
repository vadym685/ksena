package com.company.ksena.entity.inventory;

import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskDocument;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "KSENA_INVENTORY_WRAPPER")
@Entity(name = "ksena_InventoryWrapper")
public class InventoryWrapper extends StandardEntity {
    private static final long serialVersionUID = -1631449669738376720L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVENTORY_ID")
    protected Inventory inventory;

    @Column(name = "NOTE_INVENTORY")
    protected String noteInventory;

    @Column(name = "QUANTITY_INVENTORY")
    protected Integer quantityInventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_DOCUMENTS_ID")
    protected TaskDocument taskDocuments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID")
    protected Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskDocument getTaskDocuments() {
        return taskDocuments;
    }

    public void setTaskDocuments(TaskDocument taskDocuments) {
        this.taskDocuments = taskDocuments;
    }

    public TaskDocument getTaskDocument() {
        return taskDocuments;
    }

    public void setTaskDocument(TaskDocument taskDocument) {
        this.taskDocuments = taskDocument;
    }

    public Integer getQuantityInventory() {
        return quantityInventory;
    }

    public void setQuantityInventory(Integer quantityInventory) {
        this.quantityInventory = quantityInventory;
    }

    public String getNoteInventory() {
        return noteInventory;
    }

    public void setNoteInventory(String noteInventory) {
        this.noteInventory = noteInventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}