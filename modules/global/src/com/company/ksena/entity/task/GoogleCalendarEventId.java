package com.company.ksena.entity.task;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "KSENA_GOOGLE_CALENDAR_EVENT_ID")
@Entity(name = "ksena_GoogleCalendarEventId")
public class GoogleCalendarEventId extends StandardEntity {
    private static final long serialVersionUID = 7078211014979801870L;

    @Column(name = "EVENT_ID")
    protected String eventId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID")
    protected Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}