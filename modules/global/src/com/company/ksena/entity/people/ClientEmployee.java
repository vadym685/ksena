package com.company.ksena.entity.people;

import com.company.ksena.entity.company.Company;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.*;
import java.util.List;

@Table(name = "KSENA_CLIENT_EMPLOYEE")
@NamePattern("%s|fullName")
@Entity(name = "ksena_ClientEmployee")
public class ClientEmployee extends PasportData {
    private static final long serialVersionUID = -9005267031335817417L;

    @JoinTable(name = "KSENA_COMPANY_CLIENT_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "CLIENT_EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "COMPANY_ID"))
    @ManyToMany
    protected List<Company> companies;

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}