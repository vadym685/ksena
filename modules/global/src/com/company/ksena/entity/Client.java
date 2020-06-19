package com.company.ksena.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Table(name = "KSENA_CLIENT")
@Entity(name = "ksena_Client")
public class Client extends StandardEntity {
    private static final long serialVersionUID = -9005267031335817417L;

    @NotNull
    @Column(name = "FULL_NAME", nullable = false)
    protected String fullName;

    @NotNull
    @Column(name = "PHONE_NUMBER", nullable = false)
    protected Integer phoneNumber;

    @Column(name = "SERIES", length = 3)
    protected String series;

    @Column(name = "PASPORT_NUMBER")
    protected Integer pasportNumber;

    @Column(name = "AUTHORITY")
    protected String authority;

    @Column(name = "DATE_OF_ISSUE")
    protected LocalDate dateOfIssue;

    @Column(name = "INDIVIDUAL_TAXPAYER_NUMBER")
    protected String individualTaxpayerNumber;

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

    public Integer getPasportNumber() {
        return pasportNumber;
    }

    public void setPasportNumber(Integer pasportNumber) {
        this.pasportNumber = pasportNumber;
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