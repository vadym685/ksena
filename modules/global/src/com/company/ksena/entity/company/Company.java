package com.company.ksena.entity.company;

import com.company.ksena.entity.people.ClientEmployee;
import com.company.ksena.entity.point.Point;
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

    @Column(name = "LEGAL_CITY")
    protected String legalCity;

    @Column(name = "LEGAL_STREET")
    protected String legalStreet;

    @Column(name = "LEGAL_INDEX")
    protected String legalIndex;

    @Column(name = "ACTUAL_CITY")
    protected String actualCity;

    @Column(name = "ACTUAL_STREET")
    protected String actualStreet;

    @Lob
    @Column(name = "COMMENT")
    protected String comment;

    @Column(name = "ACTUAL_INDEX")
    protected String actualIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIELD_OF_ACTIVITY_ID")
    protected FieldOfActivity fieldOfActivity;

    @Column(name = "FIELD_OF_ACTIVITY_FULL")
    protected String fieldOfActivityFull;

    @Column(name = "BILL_SEND_TYPE")
    protected String billSendType;

    @Column(name = "VAT")
    protected Integer vat;

    @Email
    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "WEBSITE")
    protected String website;

    @Column(name = "CONTACT_PHONE")
    protected String contactPhone;

    @Column(name = "INDIVIDUAL_TAXPAYER_NUMBER")
    protected String individualTaxpayerNumber;

    @OneToMany(mappedBy = "companies")
    protected List<ClientEmployee> responsibleEmployee;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "company")
    protected TaskDocument taskDocument;

    @OneToMany(mappedBy = "company")
    protected List<Point> points;

    @Column(name = "TEMP_COMPANY_TYPE")
    protected String tempCompanyType;

    @Column(name = "TEMP_COMPANY_CATEGORY")
    protected String tempCompanyCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_TYPE_ID")
    protected CompanyType companyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_CATEGORY_ID")
    protected CompanyCategory companyCategory;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setFieldOfActivity(FieldOfActivity fieldOfActivity) {
        this.fieldOfActivity = fieldOfActivity;
    }

    public FieldOfActivity getFieldOfActivity() {
        return fieldOfActivity;
    }

    public String getTempCompanyCategory() {
        return tempCompanyCategory;
    }

    public void setTempCompanyCategory(String tempCompanyCategory) {
        this.tempCompanyCategory = tempCompanyCategory;
    }

    public String getTempCompanyType() {
        return tempCompanyType;
    }

    public void setTempCompanyType(String tempCompanyType) {
        this.tempCompanyType = tempCompanyType;
    }

    public void setLegalIndex(String legalIndex) {
        this.legalIndex = legalIndex;
    }

    public String getLegalIndex() {
        return legalIndex;
    }

    public void setActualIndex(String actualIndex) {
        this.actualIndex = actualIndex;
    }

    public String getActualIndex() {
        return actualIndex;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Vat getVat() {
        return vat == null ? null : Vat.fromId(vat);
    }

    public void setVat(Vat vat) {
        this.vat = vat == null ? null : vat.getId();
    }

    public CompanyCategory getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(CompanyCategory companyCategory) {
        this.companyCategory = companyCategory;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setResponsibleEmployee(List<ClientEmployee> responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
    }

    public void setBillSendType(BillSendType billSendType) {
        this.billSendType = billSendType == null ? null : billSendType.getId();
    }

    public BillSendType getBillSendType() {
        return billSendType == null ? null : BillSendType.fromId(billSendType);
    }

    public String getFieldOfActivityFull() {
        return fieldOfActivityFull;
    }

    public void setFieldOfActivityFull(String fieldOfActivityFull) {
        this.fieldOfActivityFull = fieldOfActivityFull;
    }

    public List<ClientEmployee> getResponsibleEmployee() {
        return responsibleEmployee;
    }

    public TaskDocument getTaskDocument() {
        return taskDocument;
    }

    public void setTaskDocument(TaskDocument taskDocument) {
        this.taskDocument = taskDocument;
    }

    public String getIndividualTaxpayerNumber() {
        return individualTaxpayerNumber;
    }

    public void setIndividualTaxpayerNumber(String individualTaxpayerNumber) {
        this.individualTaxpayerNumber = individualTaxpayerNumber;
    }

    public String getLegalCity() {
        return legalCity;
    }

    public void setLegalCity(String legalCity) {
        this.legalCity = legalCity;
    }

    public String getLegalStreet() {
        return legalStreet;
    }

    public void setLegalStreet(String legalStreet) {
        this.legalStreet = legalStreet;
    }

    public String getActualCity() {
        return actualCity;
    }

    public void setActualCity(String actualCity) {
        this.actualCity = actualCity;
    }

    public String getActualStreet() {
        return actualStreet;
    }

    public void setActualStreet(String actualStreet) {
        this.actualStreet = actualStreet;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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