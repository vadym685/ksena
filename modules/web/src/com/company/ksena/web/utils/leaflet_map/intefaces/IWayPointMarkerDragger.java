package com.company.ksena.web.utils.leaflet_map.intefaces;

import org.vaadin.addon.leaflet.shared.Point;

public interface IWayPointMarkerDragger {
    void onWayPointDraggingFinish(String waypointID, Point point);
}
