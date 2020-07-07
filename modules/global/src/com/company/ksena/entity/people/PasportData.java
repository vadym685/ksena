package com.company.ksena.entity.people;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "KSENA_PASPORT_DATA")
@Entity(name = "ksena_PasportData")
public class PasportData extends StandardEntity {
    private static final long serialVersionUID = -3797793039657279647L;

    @Column(name = "FULL_NAME")
    protected String fullName;

    @Column(name = "PHONE_NUMBER")
    protected Integer phoneNumber;

    @Column(name = "PASPORT_NUMBER", length = 2)
    protected String pasportNumber;

    @Column(name = "AUTHORITY")
    protected String authority;

    @Column(name = "DATE_OF_ISSUE")
    protected LocalDate dateOfIssue;

    @Column(name = "INDIVIDUAL_TAXPAYER_NUMBER")
    protected String individualTaxpayerNumber;

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

    public String getPasportNumber() {
        return pasportNumber;
    }

    public void setPasportNumber(String pasportNumber) {
        this.pasportNumber = pasportNumber;
    }
}