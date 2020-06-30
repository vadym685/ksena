package com.company.ksena.entity.people;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@NamePattern("%s|imei")
@Table(name = "KSENA_MOBILE_PHONE")
@Entity(name = "ksena_MobilePhone")
public class MobilePhone extends StandardEntity {
    private static final long serialVersionUID = 2205073397866229338L;

    @Column(name = "EMEI")
    protected String imei;

    @Column(name = "ANDROID_VERSION")
    protected String androidVersion;

    @Column(name = "PHONE_NAME")
    protected String phoneModel;

    @JoinColumn(name = "EMPLOYEE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    protected Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}