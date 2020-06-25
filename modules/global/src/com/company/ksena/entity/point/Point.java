package com.company.ksena.entity.point;

import com.company.ksena.entity.point.Coordinates;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.Column;
import javax.persistence.Entity;

@NamePattern("%s|name")
@Entity(name = "ksena_Point")
public class Point extends Coordinates {
    private static final long serialVersionUID = 1099592836887219890L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "ADRESS")
    protected String adress;

    @Column(name = "COMENT")
    protected String coment;

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