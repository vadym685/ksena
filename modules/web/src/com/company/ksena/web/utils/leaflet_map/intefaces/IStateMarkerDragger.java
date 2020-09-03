package com.company.ksena.web.utils.leaflet_map.intefaces;

import org.vaadin.addon.leaflet.shared.Point;

public interface IStateMarkerDragger {
    void onStateAndWayPointBindingFinish(String stateID, String waypointID);

    void onStatePointDraggingFinish(String stateID, Point point);
}

