package com.company.ksena.entity.people;

import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskDocument;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Table(name = "KSENA_EMPLOYEE")
@NamePattern("%s|fullName")
@Entity(name = "ksena_Employee")
public class Employee extends PasportData {
    private static final long serialVersionUID = 774612025741552324L;

    @Column(name = "EMPLOYEE_TYPE")
    protected String employeeType;

    @Column(name = "FULL_NAME_PRONUNCIATION")
    protected String fullNamePronunciation;

    @Column(name = "IS_ACTIVE")
    protected Boolean isActive;

    @Column(name = "QUALIFICATION")
    protected String qualification;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOBILE_PHONE_ID")
    protected MobilePhone mobilePhone;

    @JoinTable(name = "KSENA_TASK_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "TASK_ID"))
    @ManyToMany
    protected List<Task> tasks;

    @Column(name = "DATE_OF_EMPLOYMENT")
    protected LocalDate dateOfEmployment;

    @Column(name = "DATE_OF_DISMISSAL")
    protected LocalDate dateOfDismissal;

    @OneToOne
    @JoinColumn(name = "IMAGE_FILE_ID",
            referencedColumnName = "ID")
    protected FileDescriptor imageFile;

    @Column(name = "COMENT")
    protected String coment;

    @Column(name = "PERSONAL_PHONE_NUMBER")
    protected String personalPhoneNumber;

    @Column(name = "RELATIVES_PHONE_NUMBER")
    protected String relativesPhoneNumber;

    @Column(name = "RESIDENCE_NUMBER")
    protected String residenceNumber;

    @Column(name = "NATIONALITY")
    protected String nationality;

    @Column(name = "RESIDENCE_PERMANENT")
    protected Boolean residencePermanent;

    @Column(name = "RESIDENCE_END_TIME")
    protected LocalDate residenceEndTime;

    @Column(name = "RESIDENCE_ADDRESS")
    protected String residenceAddress;

    @Column(name = "RESIDENCE_PLACE")
    protected String residencePlace;
    @JoinTable(name = "KSENA_TASK_DOCUMENT_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "TASK_DOCUMENT_ID"))
    @ManyToMany
    protected List<TaskDocument> taskDocuments;

    public List<TaskDocument> getTaskDocuments() {
        return taskDocuments;
    }

    public void setTaskDocuments(List<TaskDocument> taskDocuments) {
        this.taskDocuments = taskDocuments;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFullNamePronunciation() {
        return fullNamePronunciation;
    }

    public void setFullNamePronunciation(String fullNamePronunciation) {
        this.fullNamePronunciation = fullNamePronunciation;
    }

    public String getRelativesPhoneNumber() {
        return relativesPhoneNumber;
    }

    public void setRelativesPhoneNumber(String relativesPhoneNumber) {
        this.relativesPhoneNumber = relativesPhoneNumber;
    }

    public String getPersonalPhoneNumber() {
        return personalPhoneNumber;
    }

    public void setPersonalPhoneNumber(String personalPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public void setImageFile(FileDescriptor imageFile) {
        this.imageFile = imageFile;
    }

    public FileDescriptor getImageFile() {
        return imageFile;
    }

    public String getResidencePlace() {
        return residencePlace;
    }

    public void setResidencePlace(String residencePlace) {
        this.residencePlace = residencePlace;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public LocalDate getResidenceEndTime() {
        return residenceEndTime;
    }

    public void setResidenceEndTime(LocalDate residenceEndTime) {
        this.residenceEndTime = residenceEndTime;
    }

    public Boolean getResidencePermanent() {
        return residencePermanent;
    }

    public void setResidencePermanent(Boolean residencePermanent) {
        this.residencePermanent = residencePermanent;
    }

    public String getResidenceNumber() {
        return residenceNumber;
    }

    public void setResidenceNumber(String residenceNumber) {
        this.residenceNumber = residenceNumber;
    }

    public Qualification getQualification() {
        return qualification == null ? null : Qualification.fromId(qualification);
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification == null ? null : qualification.getId();
    }


    public LocalDate getDateOfDismissal() {
        return dateOfDismissal;
    }

    public void setDateOfDismissal(LocalDate dateOfDismissal) {
        this.dateOfDismissal = dateOfDismissal;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public MobilePhone getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(MobilePhone mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public EmployeeType getEmployeeType() {
        return employeeType == null ? null : EmployeeType.fromId(employeeType);
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType == null ? null : employeeType.getId();
    }

}