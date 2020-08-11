package com.company.ksena.entity.company;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "KSENA_COMPANY_TYPE")
@Entity(name = "ksena_CompanyType")
public class CompanyType extends StandardEntity {
    private static final long serialVersionUID = -2849162584849622913L;

    @Column(name = "NAME")
    protected String name;

    @OneToMany(mappedBy = "companyType")
    protected List<Company> companies;

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}