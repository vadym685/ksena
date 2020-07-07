package com.company.ksena.entity.company;

import com.company.ksena.entity.people.ClientEmployee;
import com.company.ksena.entity.task.TaskDocument;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "KSENA_COMPANY")
@Entity(name = "ksena_Company")
public class Company extends StandardEntity {
    private static final long serialVersionUID = -2425137213953073615L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "FULL_NAME")
    protected String fullName;

    @Column(name = "LEGAL_ADDRESS")
    protected String legalAddress;

    @Column(name = "ACTUAL_ADDRESS")
    protected String actualAddress;

    @Email
    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "CONTACT_PHONE")
    protected Integer contactPhone;

    @Column(name = "INDIVIDUAL_TAXPAYER_NUMBER")
    protected String individualTaxpayerNumber;

    @JoinTable(name = "KSENA_COMPANY_CLIENT_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "COMPANY_ID"),
            inverseJoinColumns = @JoinColumn(name = "CLIENT_EMPLOYEE_ID"))
    @ManyToMany
    protected List<ClientEmployee> responsibleEmployee;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "company")
    protected TaskDocument taskDocument;

    public TaskDocument getTaskDocument() {
        return taskDocument;
    }

    public void setTaskDocument(TaskDocument taskDocument) {
        this.taskDocument = taskDocument;
    }

    public List<ClientEmployee> getResponsibleEmployee() {
        return responsibleEmployee;
    }

    public void setResponsibleEmployee(List<ClientEmployee> responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
    }

    public String getIndividualTaxpayerNumber() {
        return individualTaxpayerNumber;
    }

    public void setIndividualTaxpayerNumber(String individualTaxpayerNumber) {
        this.individualTaxpayerNumber = individualTaxpayerNumber;
    }

    public String getActualAddress() {
        return actualAddress;
    }

    public void setActualAddress(String actualAddress) {
        this.actualAddress = actualAddress;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(Integer contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}