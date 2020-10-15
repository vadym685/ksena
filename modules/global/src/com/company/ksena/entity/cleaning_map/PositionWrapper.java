package com.company.ksena.entity.cleaning_map;

import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskDocument;
import com.company.ksena.entity.template.Template;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "KSENA_POSITION_WRAPPER")
@Entity(name = "ksena_PositionWrapper")
public class PositionWrapper extends StandardEntity {
    private static final long serialVersionUID = 3420619926977843733L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSITION_ID")
    protected CleaningPosition position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID")
    private Template template;

    @Column(name = "PRIORITY_CLEANING_POSITION")
    protected Integer priorityCleaningPosition;

    @Column(name = "NOTE_CLEANING_POSITION")
    protected String noteCleaningPosition;

    @ManyToOne
    @JoinColumn(name = "TASK_DOCUMENTS_ID")
    protected TaskDocument taskDocuments;

    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    protected Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_NAME_ID")
    protected Room roomName;

    public void setRoomName(Room roomName) {
        this.roomName = roomName;
    }

    public Room getRoomName() {
        return roomName;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setTaskDocuments(TaskDocument taskDocuments) {
        this.taskDocuments = taskDocuments;
    }

    public TaskDocument getTaskDocuments() {
        return taskDocuments;
    }

    public String getNoteCleaningPosition() {
        return noteCleaningPosition;
    }

    public void setNoteCleaningPosition(String noteCleaningPosition) {
        this.noteCleaningPosition = noteCleaningPosition;
    }

    public Integer getPriorityCleaningPosition() {
        return priorityCleaningPosition;
    }

    public void setPriorityCleaningPosition(Integer priorityCleaningPosition) {
        this.priorityCleaningPosition = priorityCleaningPosition;
    }


    public CleaningPosition getPosition() {
        return position;
    }

    public PositionWrapper setPosition(CleaningPosition position) {
        this.position = position;
        return null;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}