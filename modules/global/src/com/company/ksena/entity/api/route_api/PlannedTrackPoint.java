package com.company.ksena.entity.api.route_api;

import java.io.Serializable;
import java.util.List;

public class PlannedTrackPoint  implements Serializable {

    public boolean found_alternative;
    public HintData hint_data;
    public List<String> route_geometry;
    public List<String> route_name;
    public RouteSummary route_summary;
    public int status = 0;
    public String status_message;
    public List<Integer> via_indices;
    public List<Object> via_points;
    public List<Object> route_geometry_splited;

    public boolean isFound_alternative() {
        return found_alternative;
    }

    public void setFound_alternative(boolean found_alternative) {
        this.found_alternative = found_alternative;
    }

    public HintData getHint_data() {
        return hint_data;
    }

    public void setHint_data(HintData hint_data) {
        this.hint_data = hint_data;
    }

    public List<String> getRoute_geometry() {
        return route_geometry;
    }

    public void setRoute_geometry(List<String> route_geometry) {
        this.route_geometry = route_geometry;
    }

    public List<String> getRoute_name() {
        return route_name;
    }

    public void setRoute_name(List<String> route_name) {
        this.route_name = route_name;
    }

    public RouteSummary getRoute_summary() {
        return route_summary;
    }

    public void setRoute_summary(RouteSummary route_summary) {
        this.route_summary = route_summary;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public List<Integer> getVia_indices() {
        return via_indices;
    }

    public void setVia_indices(List<Integer> via_indices) {
        this.via_indices = via_indices;
    }

    public List<Object> getVia_points() {
        return via_points;
    }

    public void setVia_points(List<Object> via_points) {
        this.via_points = via_points;
    }

    public List<Object> getRoute_geometry_splited() {
        return route_geometry_splited;
    }

    public void setRoute_geometry_splited(List<Object> route_geometry_splited) {
        this.route_geometry_splited = route_geometry_splited;
    }
}