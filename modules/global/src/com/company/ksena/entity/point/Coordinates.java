package com.company.ksena.entity.point;

import com.haulmont.chile.core.annotations.NumberFormat;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "KSENA_COORDINATES")
@Entity(name = "ksena_Coordinates")
public class Coordinates extends StandardEntity {
    private static final long serialVersionUID = 1090973522638241479L;

    @NumberFormat(pattern = "##,##0.00000")
    @Column(name = "LATITUDE")
    protected Double latitude;

    @NumberFormat(pattern = "##,##0.00000")
    @Column(name = "LONGITUDE")
    protected Double longitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}