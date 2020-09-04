package com.company.ksena.web.utils.leaflet_map.entitys;

public class ArrivalPointByGps {

    private double distance;
    private double duration;
    private double possibilityOfVisit;
    private boolean isInWayPointWindow;

    public final double getDistance() {
        return this.distance;
    }

    public final void setDistance(double var1) {
        this.distance = var1;
    }

    public final double getDuration() {
        return this.duration;
    }

    public final void setDuration(double var1) {
        this.duration = var1;
    }

    public final double getPossibilityOfVisit() {
        return this.possibilityOfVisit;
    }

    public final void setPossibilityOfVisit(double var1) {
        this.possibilityOfVisit = var1;
    }

    public final boolean isInWayPointWindow() {
        return this.isInWayPointWindow;
    }

    public final void setInWayPointWindow(boolean var1) {
        this.isInWayPointWindow = var1;
    }
}
