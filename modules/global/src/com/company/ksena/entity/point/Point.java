package com.company.ksena.entity.point;

import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.people.ClientEmployee;
import com.company.ksena.entity.task.Task;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.NumberFormat;

import javax.persistence.*;
import java.util.List;

@NamePattern("%s|name")
@Entity(name = "ksena_Point")
public class Point extends Coordinates {
    private static final long serialVersionUID = 1099592836887219890L;

    @Column(name = "NAME")
    protected String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    protected Company company;

    @Column(name = "POSTCODE")
    protected String postcode;

    @Column(name = "CITY")
    protected String city;

    @Column(name = "STREET")
    protected String street;

    @Column(name = "HOUSE_NUMBER")
    protected String houseNumber;

    @Column(name = "COMMENT")
    protected String comment;

    @OneToMany(mappedBy = "point")
    protected List<Task> point;

    @JoinTable(name = "KSENA_CLIENT_EMPLOYEE_POINT_LINK",
            joinColumns = @JoinColumn(name = "POINT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CLIENT_EMPLOYEE_ID"))
    @ManyToMany
    protected List<ClientEmployee> clientEmployees;

    @Column(name = "OBJECT_ACCESS")
    protected String objectAccess;

    @NumberFormat(pattern = "#,##0")
    @Column(name = "POINT_AREA")
    protected Double area;

    @Column(name = "GET_TO_OBJECT")
    protected String getToObject;

    @Column(name = "IS_CLEANING_BOOK")
    protected Boolean isCleaningBook;

    public Boolean getIsCleaningBook() {
        return isCleaningBook;
    }

    public void setIsCleaningBook(Boolean isCleaningBook) {
        this.isCleaningBook = isCleaningBook;
    }

    public String getGetToObject() {
        return getToObject;
    }

    public void setGetToObject(String getToObject) {
        this.getToObject = getToObject;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getObjectAccess() {
        return objectAccess;
    }

    public void setObjectAccess(String objectAccess) {
        this.objectAccess = objectAccess;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public List<ClientEmployee> getClientEmployees() {
        return clientEmployees;
    }

    public void setClientEmployees(List<ClientEmployee> clientEmployees) {
        this.clientEmployees = clientEmployees;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Task> getPoint() {
        return point;
    }

    public void setPoint(List<Task> point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String coment) {
        this.comment = coment;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}