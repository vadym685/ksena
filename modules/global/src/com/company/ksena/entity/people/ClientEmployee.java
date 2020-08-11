package com.company.ksena.entity.people;

import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.point.Point;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.*;
import java.util.List;

@Table(name = "KSENA_CLIENT_EMPLOYEE")
@NamePattern("%s|fullName")
@Entity(name = "ksena_ClientEmployee")
public class ClientEmployee extends PasportData {
    private static final long serialVersionUID = -9005267031335817417L;

    @JoinTable(name = "KSENA_CLIENT_EMPLOYEE_POINT_LINK",
            joinColumns = @JoinColumn(name = "CLIENT_EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "POINT_ID"))
    @ManyToMany
    protected List<Point> points;

    @Column(name = "POSITION_")
    protected String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIES_ID")
    protected Company companies;

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

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