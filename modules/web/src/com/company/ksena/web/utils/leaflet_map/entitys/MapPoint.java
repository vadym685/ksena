package com.company.ksena.web.utils.leaflet_map.entitys;

import org.vaadin.addon.leaflet.shared.Point;

public class MapPoint extends Point {
    private String time = "";
    private long id = 0L;

    public MapPoint(Double lat, Double lon) {
        super(lat,lon);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}