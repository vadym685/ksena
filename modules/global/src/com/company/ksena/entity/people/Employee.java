package com.company.ksena.entity.people;

import com.company.ksena.entity.task.TaskDocument;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.*;
import java.util.List;

@NamePattern("%s|fullName")
@Entity(name = "ksena_Employee")
public class Employee extends PasportData {
    private static final long serialVersionUID = 774612025741552324L;

    @Column(name = "EMPLOYEE_TYPE")
    protected String employeeType;
    @JoinTable(name = "KSENA_TASK_DOCUMENT_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"))
    @ManyToMany
    protected List<TaskDocument> taskDocuments;

    public List<TaskDocument> getTaskDocuments() {
        return taskDocuments;
    }

    public void setTaskDocuments(List<TaskDocument> taskDocuments) {
        this.taskDocuments = taskDocuments;
    }

    public EmployeeType getEmployeeType() {
        return employeeType == null ? null : EmployeeType.fromId(employeeType);
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType == null ? null : employeeType.getId();
    }

}