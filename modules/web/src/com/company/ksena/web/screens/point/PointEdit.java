package com.company.ksena.web.screens.point;

import com.company.ksena.entity.api.google_api.GeocodeResponse;
import com.company.ksena.entity.api.google_api.Geometry;
import com.company.ksena.entity.server_constants.ServerConstants;
import com.company.ksena.service.google_api_service.GoogleApiService;
import com.company.ksena.service.google_api_service.RepoService;
import com.company.ksena.web.utils.leaflet_map.MapHelper;
import com.company.ksena.web.utils.leaflet_map.intefaces.IWayPointMarkerDragger;
import com.google.common.base.Strings;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.point.Point;
import com.vaadin.ui.MenuBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Objects;

@UiController("ksena_Point.edit")
@UiDescriptor("point-edit.xml")
@EditedEntityContainer("pointDc")
@LoadDataBeforeShow
public class PointEdit extends StandardEditor<Point> implements IWayPointMarkerDragger {

    private static final Logger LOG = LoggerFactory.getLogger(PointEdit.class);
    @Inject
    private InstanceContainer<Point> pointDc;
    @Inject
    private VBoxLayout mapBox;

    private MapHelper mapHelper;
    @Inject
    private TextField<String> cityField;
    @Inject
    private TextField<String> streetField;
    @Inject
    private TextField<String> houseNumberField;
    @Inject
    private TextField<Double> latitudeField;
    @Inject
    private TextField<Double> longitudeField;
    @Inject
    private GoogleApiService googleApiService;
    @Inject
    private RepoService repoService;

    private HashMap<String, MenuBar.Command> commandsMap = new HashMap<String, MenuBar.Command>() {
        {
            put("addMarker", new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    LOG.info("");
                    if (pointDc.getItem().getLatitude() != null & pointDc.getItem().getLongitude() != null)
                        addWayPointMarker(pointDc.getItem().getLatitude(), pointDc.getItem().getLongitude());
                    else
                        addMarkerToCenterOfMap();
                }
            });
        }
    };

    @Subscribe
    public void onInit(InitEvent event) {
        mapHelper = new MapHelper();
        mapHelper.setWaypointDraggingListener(this);
        mapHelper.attachToContainer(mapBox);

        googleApiService.init();

        cityField.addTextChangeListener(listener -> {
            if (isAddressFieldsFilled()) {
                getCoordinatesAndDrawMarker();
            }
        });
        streetField.addTextChangeListener(listener -> {
            if (isAddressFieldsFilled()) {
                getCoordinatesAndDrawMarker();
            }
        });
        houseNumberField.addTextChangeListener(listener -> {
            if (isAddressFieldsFilled()) {
                getCoordinatesAndDrawMarker();
            }
        });
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (Objects.nonNull(getEditedEntity().getLongitude()) && Objects.nonNull(getEditedEntity().getLatitude())) {
            addWayPointMarker(getEditedEntity().getLatitude(), getEditedEntity().getLongitude());
        } else {
            if (Objects.nonNull(getEditedEntity().getCity()) && Objects.nonNull(getEditedEntity().getStreet()) && Objects.nonNull(getEditedEntity().getHouseNumber())) {
                try {
                    getCoordinatesAndDrawMarker();
                } catch (Exception e) {
                    LOG.error("");
                }
            } else {
                addMarkerToCenterOfMap();
            }
        }
    }

    private void getCoordinatesAndDrawMarker() {
        ServerConstants serverConstants = repoService.getServerConstants();
        GeocodeResponse response = googleApiService.getGeocodeByAddress(serverConstants.getGoogleToken(), getEditedEntity().getCity()
                + " "
                + getEditedEntity().getStreet()
                + " "
                + getEditedEntity().getHouseNumber());
        Geometry responseGeo = Objects.requireNonNull(response)
                .getResults()
                .get(0)
                .getGeometry();
        pointDc.getItem().setLatitude(responseGeo.getLocation().getLat());
        pointDc.getItem().setLongitude(responseGeo.getLocation().getLng());
        addWayPointMarker(latitudeField.getValue().doubleValue(), longitudeField.getValue().doubleValue());
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        latitudeField.setValue(pointDc.getItem().getLatitude());
        longitudeField.setValue(pointDc.getItem().getLongitude());
    }


    @Override
    public void onWayPointDraggingFinish(String waypointID, org.vaadin.addon.leaflet.shared.Point point) {
        LOG.info("");
        getEditedEntity().setLatitude(point.getLat());
        getEditedEntity().setLongitude(point.getLon());
    }

    private void addWayPointMarker(Double lat, Double lon) {
        LOG.info("");

        try {
            mapHelper.clearWaypointMarkersLayer();

            mapHelper.flyTo(lat, lon);
            mapHelper.addPointMarker(
                    lat,
                    lon,
                    getEditedEntity().getId().toString()
            );
        } catch (Exception ex) {
            LOG.error("", ex);
        }
    }

    private void addMarkerToCenterOfMap() {
        LOG.info("");
        org.vaadin.addon.leaflet.shared.Point center = mapHelper.getCenterPoint();
        addWayPointMarker(center.getLat(), center.getLon());
        latitudeField.setValue(center.getLat());
        longitudeField.setValue(center.getLon());
    }

    private boolean isAddressFieldsFilled() {
        return !(Strings.isNullOrEmpty(getEditedEntity().getCity()) && Strings.isNullOrEmpty(getEditedEntity().getStreet()) && Strings.isNullOrEmpty(getEditedEntity().getHouseNumber()));
    }

}