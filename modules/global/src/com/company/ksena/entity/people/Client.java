package com.company.ksena.entity.people;

import com.company.ksena.entity.point.Point;
import com.company.ksena.entity.task.TaskDocument;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.*;

@NamePattern("%s|fullName")
@Entity(name = "ksena_Client")
public class Client extends PasportData {
    private static final long serialVersionUID = -9005267031335817417L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_ID")
    protected Point point;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "client")
    protected TaskDocument taskDocument;

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public TaskDocument getTaskDocument() {
        return taskDocument;
    }

    public void setTaskDocument(TaskDocument taskDocument) {
        this.taskDocument = taskDocument;
    }

}