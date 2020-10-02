package com.company.ksena.entity.cleaning_map;

import com.company.ksena.entity.inventory.ExpendableMaterial;
import com.company.ksena.entity.task.Task;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@NamePattern("%s|name")
@Table(name = "KSENA_CLEANING_POSITION")
@Entity(name = "ksena_CleaningPosition")
public class CleaningPosition extends StandardEntity {
    private static final long serialVersionUID = -2619634825295586517L;

    @Column(name = "NAME")
    protected String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    protected Room room;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "NEED_TIME")
    protected LocalTime needTime;

    @Column(name = "PRICE")
    protected Double price;

    @Column(name = "STANDART_POSITION")
    protected Boolean standartPosition;

    @JoinTable(name = "KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK",
            joinColumns = @JoinColumn(name = "CLEANING_POSITION_ID"),
            inverseJoinColumns = @JoinColumn(name = "EXPENDABLE_MATERIAL_ID"))
    @ManyToMany
    protected List<ExpendableMaterial> expendableMaterials;

    @OneToMany(mappedBy = "position")
    protected List<PositionWrapper> positionWrapper;

    @JoinTable(name = "KSENA_TASK_CLEANING_POSITION_LINK",
            joinColumns = @JoinColumn(name = "CLEANING_POSITION_ID"),
            inverseJoinColumns = @JoinColumn(name = "TASK_ID"))
    @ManyToMany
    protected List<Task> tasks;

    public List<PositionWrapper> getPositionWrapper() {
        return positionWrapper;
    }

    public void setPositionWrapper(List<PositionWrapper> positionWrapper) {
        this.positionWrapper = positionWrapper;
    }

//    public Boolean getStandartPosition() {
//        return Objects.isNull(standartPosition) ? false : true;
//    }

    public Boolean getStandartPosition() {
        return standartPosition;
    }
    public void setStandartPosition(Boolean standartPosition) {
        this.standartPosition = standartPosition;

    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setNeedTime(LocalTime needTime) {
        this.needTime = needTime;
    }

    public LocalTime getNeedTime() {
        return needTime;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<ExpendableMaterial> getExpendableMaterials() {
        return expendableMaterials;
    }

    public void setExpendableMaterials(List<ExpendableMaterial> expendableMaterials) {
        this.expendableMaterials = expendableMaterials;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}