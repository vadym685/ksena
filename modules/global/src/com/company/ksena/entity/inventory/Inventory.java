package com.company.ksena.entity.inventory;

import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskDocument;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
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
    @JoinTable(name = "KSENA_TASK_DOCUMENT_INVENTORY_LINK",
            joinColumns = @JoinColumn(name = "INVENTORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"))
    @ManyToMany
    protected List<TaskDocument> taskDocuments;
    @JoinTable(name = "KSENA_TASK_INVENTORY_LINK",
            joinColumns = @JoinColumn(name = "INVENTORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "TASK_ID"))
    @ManyToMany
    protected List<Task> tasks;

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