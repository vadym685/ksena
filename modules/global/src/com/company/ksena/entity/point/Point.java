package com.company.ksena.entity.point;

import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.people.ClientEmployee;
import com.company.ksena.entity.task.Task;
import com.haulmont.chile.core.annotations.NamePattern;

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

    @Column(name = "ADRESS")
    protected String adress;

    @Column(name = "COMENT")
    protected String coment;

    @OneToMany(mappedBy = "point")
    protected List<Task> point;
    @JoinTable(name = "KSENA_CLIENT_EMPLOYEE_POINT_LINK",
            joinColumns = @JoinColumn(name = "POINT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CLIENT_EMPLOYEE_ID"))
    @ManyToMany
    protected List<ClientEmployee> clientEmployees;

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

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}