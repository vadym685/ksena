package com.company.ksena.web.screens.point;

import com.company.ksena.web.utils.leaflet_map.MapHelper;
import com.company.ksena.web.utils.leaflet_map.intefaces.IWayPointMarkerDragger;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.point.Point;

import javax.inject.Inject;

@UiController("ksena_Point.edit")
@UiDescriptor("point-edit.xml")
@EditedEntityContainer("pointDc")
@LoadDataBeforeShow
public class PointEdit extends StandardEditor<Point> implements IWayPointMarkerDragger {

    @Inject
    private VBoxLayout mapBox;

    private MapHelper mapHelper;

//    @Subscribe
//    public void onAfterShow(AfterShowEvent event) {
//
//    }

    @Subscribe
    public void onInit(InitEvent event) {
        mapHelper = new MapHelper();
        mapHelper.setWaypointDraggingListener(this);
        mapHelper.attachToContainer(mapBox);
    }


    @Override
    public void onWayPointDraggingFinish(String waypointID, org.vaadin.addon.leaflet.shared.Point point) {
//        LOG.info("");
        getEditedEntity().setLatitude(point.getLat());
        getEditedEntity().setLongitude(point.getLon());
    }
}