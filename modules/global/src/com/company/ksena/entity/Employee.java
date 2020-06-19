package com.company.ksena.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Table(name = "KSENA_EMPLOYEE")
@Entity(name = "ksena_Employee")
public class Employee extends StandardEntity {
    private static final long serialVersionUID = 774612025741552324L;

    @NotNull
    @Column(name = "FULL_NAME", nullable = false)
    protected String fullName;

    @Column(name = "PHONE_NUMBER")
    protected Integer phoneNumber;

    @Column(name = "SERIES")
    protected String series;

    @Column(name = "PASPORT_NUMBER")
    protected Integer pasportNumber;

    @Column(name = "AUTHORITY")
    protected String authority;

    @Column(name = "DATEOF_ISSUE")
    protected LocalDate dateOfIssue;

    @Column(name = "INDIVIDUAL_TAXPAYER_NUMBER")
    protected String individualTaxpayerNumber;

    @Column(name = "EMPLOYEE_TYPE")
    protected String employeeType;

    public EmployeeType getEmployeeType() {
        return employeeType == null ? null : EmployeeType.fromId(employeeType);
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType == null ? null : employeeType.getId();
    }

    public void setPasportNumber(Integer pasportNumber) {
        this.pasportNumber = pasportNumber;
    }

    public Integer getPasportNumber() {
        return pasportNumber;
    }

    public String getIndividualTaxpayerNumber() {
        return individualTaxpayerNumber;
    }

    public void setIndividualTaxpayerNumber(String individualTaxpayerNumber) {
        this.individualTaxpayerNumber = individualTaxpayerNumber;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}