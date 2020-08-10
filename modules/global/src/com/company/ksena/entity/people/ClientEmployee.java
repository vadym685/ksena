package com.company.ksena.entity.people;

import com.company.ksena.entity.company.Company;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.*;

@Table(name = "KSENA_CLIENT_EMPLOYEE")
@NamePattern("%s|fullName")
@Entity(name = "ksena_ClientEmployee")
public class ClientEmployee extends PasportData {
    private static final long serialVersionUID = -9005267031335817417L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIES_ID")
    protected Company companies;

    @Column(name = "POSITION_")
    protected String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCompanies(Company companies) {
        this.companies = companies;
    }

    public Company getCompanies() {
        return companies;
    }
}