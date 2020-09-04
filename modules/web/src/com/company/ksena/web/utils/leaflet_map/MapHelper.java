package com.company.ksena.web.utils.leaflet_map;

import com.company.ksena.entity.api.gps_gt_api.TrackMessage;
import com.company.ksena.web.utils.Utils;
import com.company.ksena.web.utils.leaflet_map.entitys.MapPoint;
import com.company.ksena.web.utils.leaflet_map.intefaces.IMapMarkerDragger;
import com.company.ksena.web.utils.leaflet_map.intefaces.IRouteMarkerDragger;
import com.company.ksena.web.utils.leaflet_map.intefaces.IStateMarkerDragger;
import com.company.ksena.web.utils.leaflet_map.intefaces.IWayPointMarkerDragger;
import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.global.Resources;
import com.haulmont.cuba.gui.components.BoxLayout;
import com.haulmont.cuba.web.widgets.addons.contextmenu.ContextMenu;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.leaflet.*;
import org.vaadin.addon.leaflet.shared.Bounds;
import org.vaadin.addon.leaflet.shared.Point;

import javax.inject.Inject;
import java.util.*;

import static com.company.ksena.web.utils.Constants.*;
import static com.company.ksena.web.utils.leaflet_map.markers.IconsContainer.*;


public class MapHelper {

    private Logger LOG = LoggerFactory.getLogger(MapHelper.class);

    public final double DEFAULT_LATITUDE = 50.401699;
    public final double DEFAULT_LONGITUDE = 30.252512;
    private final double ZOOM_LEVEL = 10.0;
    private final double MAX_ZOOM_LEVEL = 18.0;
    private final double SHOW_POINT_ZOOM_LEVEL = 15.0;

    private double lastZoon = ZOOM_LEVEL;
    private double waypointParkingRadius = 150.0;

    private ContextMenu contextMenu;

    public void setWaypointParkingRadius(double waypointParkingRadius) {
        if (waypointParkingRadius > 0.0) {
            this.waypointParkingRadius = waypointParkingRadius;
        }
    }

    public void setShowParkingRadius(boolean showParkingRadius) {
        this.showParkingRadius = showParkingRadius;
    }

    private boolean showParkingRadius = true;
    private boolean isDraggableWayPoints = false;

    public void setDraggableWayPoints(boolean draggableWayPoints) {
        isDraggableWayPoints = draggableWayPoints;
        updateDraggableOfWayPoint(draggableWayPoints);
    }

    private LMap map;
    private IMapMarkerDragger draggingListener;
    private IStateMarkerDragger stateDraggingListener;
    private IWayPointMarkerDragger waypointDraggingListener;
    private IRouteMarkerDragger routepointDraggingListener;

    public void setUniversalDraggingListener(IMapMarkerDragger draggingListener) {
        this.draggingListener = draggingListener;
        stateDraggingListener = draggingListener;
        waypointDraggingListener = draggingListener;
        routepointDraggingListener = draggingListener;
    }


    //Слой полилинии фактического пути авто
    private LLayerGroup factualTrackLayout = new LLayerGroup();

    // Слой маркеров фактического трека - изменеяется в зависимости от зума - нужен для отслежнивания направления авто
    private LLayerGroup factualTrackPointsLayout = new LLayerGroup();

    // Слой точек стопов транспорта
    private LLayerGroup factualTrackStatesLayout = new LLayerGroup();

    // Слой маркеров текущего положения автомобилей
    private LLayerGroup currentPositionLayout = new LLayerGroup();

    //Слой полилинии планового пути авто
    private LLayerGroup plannedRouteTrackLayer = new LLayerGroup();

    // Слой маркеров начала и окончания трека
    private LLayerGroup plannedRouteMarkersLayer = new LLayerGroup();

    // Слой маркеров точек доставки
    private LLayerGroup waypointMarkersLayout = new LLayerGroup();

    // Слой временных объектов
    private LLayerGroup temporaryLayout = new LLayerGroup();

    // Слой полигонов
    private LLayerGroup polygonsLayout = new LLayerGroup();


    // Содержит актический трек транспорта - нужен для перерисовки данных на карте и ручной отметки посещаемости точек
    private ArrayList<MapPoint> routePoints = new ArrayList<>();

    private ArrayList<LLayerGroup> allLayers = new ArrayList<LLayerGroup>() {
        {
            add(plannedRouteTrackLayer);
            add(factualTrackLayout);
            add(waypointMarkersLayout);
            add(currentPositionLayout);
            add(temporaryLayout);
            add(factualTrackPointsLayout);
            add(plannedRouteMarkersLayer);
            add(factualTrackStatesLayout);
            add(polygonsLayout);
        }
    };


    @Inject
    private FileStorageService fileStorageService;

    @Inject
    protected Resources resources;

    private HashMap<String, MarkerSettings> markerSettingsRepo = new HashMap<>();


    public LMarker.DragEndListener wayPointDragging = new LMarker.DragEndListener() {
        @Override
        public void dragEnd(LMarker.DragEndEvent event) {
            LMarker marker = (LMarker) event.getComponent();
            waypointDraggingListener.onWayPointDraggingFinish(marker.getId(), marker.getPoint());
        }
    };

    private int maxPointDistance = 15;
    private LMarker.DragEndListener routePointDragging = new LMarker.DragEndListener() {

        @Override
        public void dragEnd(LMarker.DragEndEvent event) {
            LMarker selectedMarker = (LMarker) event.getComponent();
            for (Component component : waypointMarkersLayout) {
                LMarker waypoint = (LMarker) component;

                double checkedRadius = Utils.calculateDistanceBetweenGeoPoints(selectedMarker.getPoint(), waypoint.getPoint());

                if (checkedRadius < maxPointDistance) {
                    draggingListener.onRoutePointDraggingFinish(selectedMarker.getId(), waypoint.getId());
                }
            }
        }
    };

    private LMarker.DragEndListener statePointDragging = new LMarker.DragEndListener() {

        @Override
        public void dragEnd(LMarker.DragEndEvent event) {
            LMarker selectedMarker = (LMarker) event.getComponent();
            for (Component component : waypointMarkersLayout) {
                LMarker waypoint = (LMarker) component;

                double checkedRadius = Utils.calculateDistanceBetweenGeoPoints(selectedMarker.getPoint(), waypoint.getPoint());

                if (checkedRadius < maxPointDistance) {
                    draggingListener.onStateAndWayPointBindingFinish(selectedMarker.getId(), waypoint.getId());
                } else {
                    draggingListener.onStatePointDraggingFinish(selectedMarker.getId(), selectedMarker.getPoint());
                }
            }
        }
    };


    public void attachToContainer(BoxLayout mapContainer) {
        map = new LMap();
        map.setZoomLevel(lastZoon);

        map.addLayer(new LOpenStreetMapLayer());
        map.setCenter(new Point(DEFAULT_LATITUDE, DEFAULT_LONGITUDE));

        for (LLayerGroup layer : allLayers) {
            map.addComponent(layer);
        }

        mapContainer.unwrap(Layout.class)
                .addComponent(map);

        map.addMoveEndListener(new LeafletMoveEndListener() {
            @Override
            public void onMoveEnd(LeafletMoveEndEvent event) {
                updateRouteMarkers();
            }
        });

        initMarkerSettings();


//        val controlsLayout = LLayerGroup()
//        val b1 = Button("Center then Zoom")
////        b1.addClickListener{ doWork("c", "z")}
//        val b2 = Button("Zoom then Center")
////        b2.addClickListener{doWork("z", "c")}
//        val b3 = Button("setView")
////        b3.addClickListener{ doWork("v")}

//        controlsLayout.addComponents(b1, b2, b3)
//        controlsLayout.isSpacing = true
//        map.layersControl.addBaseLayer(controlsLayout,"h1")

        contextMenu = new ContextMenu(mapContainer.unwrap(AbstractComponent.class), true);
    }

    private void initMarkerSettings() {

        MarkerSettings waypointMarker = new MarkerSettings();
        waypointMarker.setxAnchorShift(0);
        waypointMarker.setyAnchorShift(32);
        waypointMarker.setIconWidth(32);
        waypointMarker.setIconHeight(32);
        markerSettingsRepo.put(WAYPOINT_MARKER, waypointMarker);

        MarkerSettings warehouseMarker = new MarkerSettings();
        warehouseMarker.setxAnchorShift(0);
        warehouseMarker.setyAnchorShift(32);
        warehouseMarker.setIconWidth(32);
        warehouseMarker.setIconHeight(32);
        markerSettingsRepo.put(WAREHOUSE_MARKER, warehouseMarker);

        MarkerSettings lastCarPositionMarker = new MarkerSettings();
        lastCarPositionMarker.setxAnchorShift(0);
        lastCarPositionMarker.setyAnchorShift(32);
        lastCarPositionMarker.setIconWidth(32);
        lastCarPositionMarker.setIconHeight(32);
        markerSettingsRepo.put(LAST_CAR_POSITION_MARKER, lastCarPositionMarker);

        MarkerSettings firstCarPositionMarker = new MarkerSettings();
        lastCarPositionMarker.setxAnchorShift(0);
        lastCarPositionMarker.setyAnchorShift(32);
        lastCarPositionMarker.setIconWidth(32);
        lastCarPositionMarker.setIconHeight(32);
        markerSettingsRepo.put(FIRST_CAR_POSITION_MARKER, firstCarPositionMarker);

    }


//    public void addMarker(Double lat, Double lon) {
//        addMarker(lat, lon, null);
//    }
//
//    public void addMarker(Double lat, Double lon, String type) {
//        addMarker(new Point(lat, lon), type);
//    }
//
//    public void addMarker(Point point, String markerType) {
//
//        LMarker marker = new LMarker(point);
//
//        if (markerType != null) {
//            setMarkerSettings(marker, markerType, null);
//        }
//    }
//
//
//    public void addMarker(Point point, String markerType, String markerText, String status) {
//
//        LMarker marker = new LMarker(point);
//
//        if (markerType != null) {
//            setMarkerSettings(marker, markerType, markerText, status, null, null);
//        }
//    }
//
//    public void addMarker(Point point, String markerType, String markerText, Color color) {
//
//        LMarker marker = new LMarker(point);
//
//        if (markerType != null) {
//            setMarkerSettings(marker, markerType, markerText, "", color, null);
//        }
//    }
//
//    public void addMarker(Point point, String markerType, String markerText, Color color, String popupText) {
//
//        LMarker marker = new LMarker(point);
//
//        if (markerType != null) {
//            setMarkerSettings(marker, markerType, markerText, "", color, popupText);
//        }
//    }

//    public void setMarkerSettings(LMarker marker, String markerType, String markerText) {
//        setMarkerSettings(marker, markerType, markerText, null, null, null)
//    }

//    public void setMarkerSettings(LMarker marker, String markerType,
//                                  String markerText, String status,
//                                  Color color, String popupText) {
//
//        //Fixme - нужно этот универсальный обработчик создания маркеров вообще переделать
//
//        MarkerSettings markerSettings = markerSettingsRepo.get(markerType);
//
//        // adding drag end listener makes marker draggable
//        if (isDraggableWayPoints)
//            marker.addDragEndListener(wayPointDragging)
//
//        if (popupText != null) {
//            marker.setPopup(popupText);
//        }
//
//
//        if (markerSettings != null) {
//
//            marker.setIconSize(new Point(0.0, 0.0)); //Don't change !!
//
//            Point iconAnchor = new Point(markerSettings.getxAnchorShift(), markerSettings.getyAnchorShift()); // x - shift to left , y - shift to up
//            marker.setIconAnchor(iconAnchor);
//
//            AbstractMarkerIconBuilder builder = new BaseMarker.Builder();
//
//            when(markerType) {
//                WAYPOINT_MARKER -> {
//                    builder = TextMarker.Builder().setText(markerText)
//                }
//                WAREHOUSE_MARKER -> {
//                    builder = IconMarker.Builder().setIcon(WAREHOUSE_BODY)
//                }
//            }
//
//            markerSettings.mainColor = when(status) {
//                TASK_STATUS_REJECTED -> WAY_POINT_MARKER_REJECTED
//                TASK_STATUS_COMPLETED -> WAY_POINT_MARKER_COMPLETED
//                else ->WAY_POINT_MARKER_CREATED
//            }
//
//            if (color != null) {
//                markerSettings.mainColor = color
//            }
//
//            builder.setMainColor(markerSettings.mainColor)
//                    .setBackgroundColor(markerSettings.backgroundColor)
//                    .setSize(markerSettings.iconWidth, markerSettings.iconHeight)
//
////                    marker.icon = ExternalResource("data:image/svg+xml;utf8,$svg")
////                    val svg =
////                    marker.icon = ExternalResource(url)
//
//            val icon = builder.build()
//            when(markerType) {
//                LAST_CAR_POSITION_MARKER -> {
//                    marker.setDivIcon(CAR_LAST_POSITION_ICON)
//                }
//                FIRST_CAR_POSITION_MARKER -> {
//                    marker.setDivIcon(CAR_FIRST_POSITION_ICON)
//                }
//                else ->{
//                    marker.setDivIcon(icon)
//                }
//            }
//
//        }
//
//        waypointMarkersLayout.addComponent(marker)
//
//        //Add way point parking radius to map
//        if (showParkingRadius) {
//            val parkingCircle = LCircle(marker.getPoint(), waypointParkingRadius)
//            parkingCircle.setColor("green")
//            factualTrackStatesLayout.addComponent(parkingCircle)
//        }
//    }

    public void addStateMarker(LMarker marker, String popupText, boolean showParkingRadius) {

        marker.addDragEndListener(statePointDragging);
        marker.setPopup(popupText);

        marker.setIconSize(new Point(0.0, 0.0)); //Don't change !!

        Point iconAnchor = new Point(7.0, 7.0);// lat - shift to left , lon - shift to up
        marker.setIconAnchor(iconAnchor);

        String icon = STATE_ITEM_ICON;
        marker.setDivIcon(icon);

        factualTrackStatesLayout.addComponent(marker);

        //Add way point parking radius to map
        if (showParkingRadius) {
            LCircle parkingCircle = new LCircle(marker.getPoint(), waypointParkingRadius);
            factualTrackStatesLayout.addComponent(parkingCircle);
        }
    }


    public void addPinMarker(Double lat, Double lon, String color) {
        LMarker marker = new LMarker(lat, lon);
        addPinMarker(marker, color);
    }

    public void addPinMarker(LMarker marker, String color) {

        LLayerGroup workLayer = plannedRouteTrackLayer;

//        marker.addDragEndListener(statePointDragging)
//        marker.setPopup(popupText)

        marker.setIconSize(new Point(0.0, 0.0)); //Don't change !!

        Point iconAnchor = new Point(20.0, 32.0); // lat - shift to left , lon - shift to up
        marker.setIconAnchor(iconAnchor);

        String icon = PIN_MARKER_ICON;
        icon = icon.replace(PIN_MARKER_ICON_COLOR, color);
        marker.setDivIcon(icon);

        workLayer.addComponent(marker);

        //Add way point parking radius to map
        if (showParkingRadius) {
            LCircle parkingCircle = new LCircle(marker.getPoint(), waypointParkingRadius);
            workLayer.addComponent(parkingCircle);
        }
    }


    public void addStartRouteMarker(Double lat, Double lon) {
        addStartRouteMarker(new Point(lat, lon));
    }

    public void addStartRouteMarker(Point point) {
        addStartRouteMarker(new LMarker(point));
    }

    public void addStartRouteMarker(LMarker marker) {

        String markerIcon = "flag_marker_green.svg";
        String divIconString = createImageMarker(SVG_POINTS_ICONS_PATH + markerIcon);
        marker.setDivIcon(divIconString);
        marker.setIconSize(new Point(0.0, 0.0));
        Point iconAnchor = new Point(-7.0, 34.0);// lat - shift to left , lon - shift to up
        marker.setIconAnchor(iconAnchor);

        factualTrackLayout.addComponent(marker);
    }

    public void addFinisRouteMarker(Double lat, Double lon) {
        addFinisRouteMarker(new Point(lat, lon));
    }

    public void addFinisRouteMarker(Point point) {
        addFinisRouteMarker(new LMarker(point));
    }

    public void addFinisRouteMarker(LMarker marker) {
        String markerIcon = "flag_marker_red.svg";
        String divIconString = createImageMarker(SVG_POINTS_ICONS_PATH + markerIcon);
        marker.setDivIcon(divIconString);
        marker.setIconSize(new Point(0.0, 0.0));
        Point iconAnchor = new Point(-7.0, 34.0); // lat - shift to left , lon - shift to up
        marker.setIconAnchor(iconAnchor);

        factualTrackLayout.addComponent(marker);
    }


    public void addFactualRouteMarker(LMarker marker, String markerType) {

//        marker.setPopup(popupText)

        marker.setIconSize(new Point(0.0, 0.0)); //Don't change !!

        Point iconAnchor = new Point(16.0, 32.0); // lat - shift to left , lon - shift to up
        marker.setIconAnchor(iconAnchor);

        switch (markerType) {
            case LAST_CAR_POSITION_MARKER:
                marker.setDivIcon(CAR_LAST_POSITION_ICON);
                break;
            case FIRST_CAR_POSITION_MARKER:
                marker.setDivIcon(CAR_FIRST_POSITION_ICON);
                break;

        }
    }

    private void updateDraggableOfWayPoint(boolean isDraggable) {

        for (Component component : waypointMarkersLayout) {

            LMarker marker = (LMarker) component;
            if (isDraggable) {
                marker.addDragEndListener(wayPointDragging);
            }
//            else {
//                marker.removeListener(LMarker.DragEndEvent::class.java, draggable)
//            }
        }
    }

    public void addPolygon() {
        LPolygon polygon = new LPolygon();
//        val points = arrayOf()
        polygon.setPoints(new Point(40.0, 40.0), new Point(30.0, 30.0), new Point(50.0, 50.0), new Point(10.0, 20.0));
        polygonsLayout.addComponent(polygon);
    }

    public void updateFactualTrack(List<TrackMessage> carTrack, TimeZone userTimeZone) {
        updateFactualTrack(carTrack, Adapter.FACTUAL_POLYLINE_COLOR, userTimeZone);
    }


    public ArrayList<MapPoint> updateFactualTrack(List<TrackMessage> carTrack, String lineColor, TimeZone userTimeZone) {
        // Переводим фактический трэк в точки, который будут и спользоваться при отрисовке
        ArrayList<MapPoint> mapPoints = new ArrayList<MapPoint>();
        for (TrackMessage coord : carTrack) {
            MapPoint newPoint = new MapPoint(coord.getLat(), coord.getLon());
            newPoint.setId(coord.t);
            newPoint.setTime(Utils.unixToString(coord.getT(), TIME_H_M_S_FORMAT, userTimeZone));
//            newPoint.getId()  = "${coord.t}"
            mapPoints.add(newPoint);
        }

        routePoints = mapPoints;

        ArrayList<Point> points = new ArrayList<Point>();
        points.addAll(mapPoints);
        LPolyline polyline = Adapter.polylineByPoints(points, lineColor);
        addPolyline(polyline, factualTrackLayout);

        //ДОбавляем координату старта и последнюю полученную координату на карту
        TrackMessage first = carTrack.get(0);
        MapPoint firstPoint = new MapPoint(first.lat, first.lon);
        firstPoint.setId(first.getT());

        TrackMessage last = carTrack.get(carTrack.size() - 1);
        MapPoint lastPoint = new MapPoint(last.lat, last.lon);
        lastPoint.setId(last.getT());

        updateRouteMarkers();

        return mapPoints;
    }

    public void updatePlanedTrack(List<MapPoint> planedTrack) {
        addPolyline(Adapter.plannedTrackToPolyline(planedTrack), plannedRouteTrackLayer);
    }


    public void updatePlanedTrack(String polylineID, List<MapPoint> planedTrack) {
        LPolyline polyline = Adapter.plannedTrackToPolyline(planedTrack);
        polyline.setId(polylineID);
        addPolyline(polyline, plannedRouteTrackLayer);
    }

    private void addPolyline(LPolyline polyline, LLayerGroup layout) {
        layout.addComponent(polyline);
    }

    private void updateRouteMarkers() {

        clearFactualTrackPointsLayout();

        double zoomLevel = map.getZoomLevel();
        Iterator<Component> iterator = factualTrackLayout.iterator();
        if (!iterator.hasNext() || zoomLevel < SHOW_POINT_ZOOM_LEVEL)
            return;

        // Определяем процент отсеиваемы точек для отрисовки
        double counterPercent = (map.getZoomLevel() / MAX_ZOOM_LEVEL);

//        val countThreshold = 100 /
        // Определяем, сколько точек вообще попадет в зону видимости окна карты
        // для этого берем центральную текущую координату и любую кряйнюю
        // определяем растояние между ними  и по этому растояни фильтруем точки , котоыре входят радиус видимости
        Point center = map.getCenter();
//        LOG.info("Center ${center.lat},${center.lon}")
        Point centerPoint = new Point(center.getLat(), center.getLon());
        LMarker centerMarker = new LMarker(centerPoint);
//        val boundsPoint = map.contentBounds.coordinates[0]
//        LOG.info("Bounds $boundsPoint")

//        val startPoint = Point2D.Double(center.lat, center.lon)
//        val endPoint = Point2D.Double(boundsPoint.y, boundsPoint.x)
//        val viewRadius = Utils.calculateDistanceBetweenGeoPoints(startPoint, endPoint)
        double viewRadius = 3000 * SHOW_POINT_ZOOM_LEVEL / zoomLevel;

        ArrayList<LMarker> sortedMarkers = new ArrayList<>();

        double maxSortingDistance = 100;
        double markerSortingDistance = maxSortingDistance * MAX_ZOOM_LEVEL / zoomLevel;
        double maxSortingTime = 120;
        double markerSortingTime = (maxSortingTime * MAX_ZOOM_LEVEL / zoomLevel);

        for (int index = 0; index < routePoints.size(); index++) {
            MapPoint point = routePoints.get(index);
//
//            //Пропускаем каждый 5 для ускорения работы алгоритма
//            if ((index + 1) % 5 == 0)
//                continue

            Point waypoint = new Point(point.getLat(), point.getLon());
            LMarker wayPointMarker = new LMarker(waypoint);

//            val distance = centerMarker.geometry.distance(wayPointMarker.geometry)
            double checkedRadius = Utils.calculateDistanceBetweenGeoPoints(centerPoint, waypoint);

            if (checkedRadius < viewRadius) {
                LMarker newMarker = createRouteMarker(wayPointMarker);
                newMarker.setId("" + point.getId());
                newMarker.setPopup(point.getTime());

                if (sortedMarkers.isEmpty()) {
                    sortedMarkers.add(newMarker);
                    continue;
                }

                LMarker lstMarker = sortedMarkers.get(sortedMarkers.size() - 1);
                double markerDistance = Utils.calculateDistanceBetweenGeoPoints(lstMarker.getPoint(), waypoint);
                long lastTime = Long.parseLong(lstMarker.getId());
                lastTime += markerSortingTime;
                long currentTime = Long.parseLong(newMarker.getId());
                if (markerDistance >= markerSortingDistance || currentTime >= lastTime) {
                    sortedMarkers.add(newMarker);
                    factualTrackPointsLayout.addComponent(newMarker);
                }
            }
        }

    }

    private LMarker createRouteMarker(LMarker marker) {
        marker.addDragEndListener(routePointDragging);
//        marker.setIconSize(Point(40.0, 40.0))
//        marker.addStyleName("leaflet-marker-track-points");
        marker.setIconSize(new Point(0.0, 0.0));//Don't change !!
        marker.setZIndexOffset(-1);
        Point iconAnchor = new Point(7.0, 7.0); // x - shift to left , y - shift to up
        marker.setIconAnchor(iconAnchor);

        String icon = ROUTE_ITEM_ICON;
        marker.setDivIcon(icon);

        return marker;
    }

    public void clearPlannedRouteTrackLayer() {
        plannedRouteTrackLayer.removeAllComponents();
    }


    public void clearFactualTrackLayout() {
        factualTrackLayout.removeAllComponents();
    }

    public void clearPolygonsLayer() {
        polygonsLayout.removeAllComponents();
    }

    public void clearWaypointMarkersLayer() {
        waypointMarkersLayout.removeAllComponents();
    }

    public void clearFactualTrackStatesLayout() {
        factualTrackStatesLayout.removeAllComponents();
    }

    public void clearFactualTrackPointsLayout() {
        factualTrackPointsLayout.removeAllComponents();
    }

    public void clearPlannedRouteMarkersLayer() {
        plannedRouteMarkersLayer.removeAllComponents();
    }

    public void clearTemporaryLayout() {
        temporaryLayout.removeAllComponents();
    }

    private void clearCurrentPositionLayout() {
        currentPositionLayout.removeAllComponents();
    }

    public void setVisibilityOfPlanedRouteTrackLayer(boolean isVisible) {
        plannedRouteTrackLayer.setVisible(isVisible);
    }

    public void clearMap() {
        clearPolygonsLayer();
        clearPlannedRouteTrackLayer();
        clearFactualTrackLayout();
        clearFactualTrackPointsLayout();
        clearPlannedRouteMarkersLayer();
        clearWaypointMarkersLayer();
        clearFactualTrackStatesLayout();
        clearTemporaryLayout();
        clearCurrentPositionLayout();
    }

    public void flyTo(Double lat, Double lon) {
        map.flyTo(new Point(lat, lon), map.getZoomLevel());
    }

    public void flyTo(Double lat, Double lon, Double zoom) {
        map.flyTo(new Point(lat, lon), zoom);
    }

    public void zoomToContent() {
        map.zoomToContent();
    }

    public void updateWaypointParkingRadius() {
        for (Component component : factualTrackStatesLayout) {
            try {
                LCircle parkingCircle = (LCircle) component;
                parkingCircle.setRadius(waypointParkingRadius);
            } catch (Exception ex) {

            }
        }
    }

    public Bounds getBounds() {
        return map.getBounds();
    }

    //Функция отображения точки доставки при открытии карты точки доставки
    public void addPointMarker(Double lat, Double lon, String id) {
        LMarker marker = new LMarker(lat, lon);

        marker.addDragEndListener(wayPointDragging);
//        marker.setIconSize(Point(40.0, 40.0))
//        marker.addStyleName("leaflet-marker-track-points");
        marker.setIconSize(new Point(0.0, 0.0)); //Don't change !!
        marker.setZIndexOffset(-1);
        Point iconAnchor = new Point(7.0, 7.0);// x - shift to left , y - shift to up
        marker.setIconAnchor(iconAnchor);
//        String icon = ROUTE_ITEM_ICON;
//        marker.setDivIcon(icon);
        marker.setId(id);
//        marker.setPopup(point.time)
        waypointMarkersLayout.addComponent(marker);
        waypointMarkersLayout.setVisible(true);
    }

    public void addSimpleMarker(Double lat, Double lon) {
        LMarker marker = new LMarker(lat, lon);
        addSimpleMarker(marker);
    }


    public void addTemporaryMarker(Double lat, Double lon) {
        LMarker marker = new LMarker(lat, lon);
        addTemporaryMarker(marker);
    }

    public void addTemporaryMarker(LMarker marker) {
        temporaryLayout.addComponent(marker);
    }

    public void addCurrentPositionMarker(LMarker marker) {
        currentPositionLayout.addComponent(marker);
    }


    public void addSimpleMarker(LMarker marker) {
        waypointMarkersLayout.addComponent(marker);
    }

    public void removeMarkerById(String id) {
        for (LLayerGroup layer : allLayers) {
            removeMarkerById(id, layer);
        }
    }


    public void removeMarkerById(String id, LLayerGroup layout) {
        try {
            for (Component item : layout) {
                if (item.getId().equals(id)) {
                    try {
                        layout.removeComponent(item);
                    } catch (Exception ex) {
//                        LOG.error("", ex)
                    }
                }
            }
        } catch (Exception ex) {
//            LOG.error("", ex)
        }
    }

    public Component updateMarkerById(String id) {
        try {
            for (Component item : waypointMarkersLayout) {
                if (item.getId().equals(id)) {
                    return item;
                }
            }
        } catch (Exception ex) {
            LOG.error("", ex);
        }

        return null;
    }

    public void updatePlanedTrack(List<MapPoint> planedTrack, String color) {
        addPolyline(Adapter.plannedTrackToPolyline(planedTrack, color), plannedRouteTrackLayer);
    }

    public void updatePlanedTrackWithDottedLine(List<MapPoint> planedTrack, String color) {
        addPolyline(Adapter.plannedTrackToPolylineWithDottedLine(planedTrack, color), plannedRouteTrackLayer);
    }

    public void updatePlanedTrackWithDottedLine(List<MapPoint> planedTrack) {
        addPolyline(Adapter.plannedTrackToPolylineWithDottedLine(planedTrack), plannedRouteTrackLayer);
    }

    public void addMarkerToLayer(LMarker marker, LLayerGroup layout) {
        layout.addComponent(marker);
    }

    public String createImageMarkerWithText(String imagePath, String style, String markerText) {
        return String.format(IMAGE_MARKER_WITH_TEXT_FORMAT, imagePath, style, markerText);
    }

    public String createImageMarkerWithColoredText(String imagePath, String textStyle, String markerText) {
        return String.format(IMAGE_MARKER_WITH_COLORED_TEXT_FORMAT, imagePath, textStyle, markerText);
    }

    public String createMarkerWithText(String markerText, String style) {
        return String.format(MARKER_WITH_TEXT_FORMAT, style, markerText);
    }

    public String createImageMarker(String imagePath) {
        return String.format(IMAGE_MARKER_FORMAT, imagePath);
    }

    public void clearFactualLayouts() {
        clearFactualTrackLayout();
        clearFactualTrackPointsLayout();
        clearFactualTrackStatesLayout();
    }


    public void updateWaypointMarker(LMarker marker, String infoMsg, LLayerGroup layout) {
        try {
            for (Component item : layout) {
                if (item.getId().equals(marker.getId())) {
                    try {
                        LMarker oldMarker = (LMarker) item;

                        oldMarker.setPopup(infoMsg);
                        oldMarker.setPoint(marker.getPoint());

                        layout.removeComponent(item);
                        layout.addComponent(item);
                    } catch (Exception ex) {
//                        LOG.error("", ex)
                    }
                }
            }
        } catch (Exception ex) {
//            LOG.error("", ex)
        }
    }

    public void updatePolylineColor(String polylineId, String color, LLayerGroup layout) {
        try {
            for (Component item : layout) {
                if (item.getId().equals(polylineId)) {
                    try {
                        LPolyline polyline = (LPolyline) item;

                        polyline.setColor(color);

                        layout.removeComponent(item);
                        layout.addComponent(item);
                    } catch (Exception ex) {
//                        LOG.error("", ex)
                    }
                }
            }
        } catch (Exception ex) {
//            LOG.error("", ex)
        }
    }

    public LLayerGroup getFactualTrackLayout() {
        return factualTrackLayout;
    }

    public LLayerGroup getFactualTrackPointsLayout() {
        return factualTrackPointsLayout;
    }

    public LLayerGroup getFactualTrackStatesLayout() {
        return factualTrackStatesLayout;
    }

    public LLayerGroup getCurrentPositionLayout() {
        return currentPositionLayout;
    }

    public LLayerGroup getPlannedRouteTrackLayer() {
        return plannedRouteTrackLayer;
    }

    public LLayerGroup getPlannedRouteMarkersLayer() {
        return plannedRouteMarkersLayer;
    }

    public LLayerGroup getWaypointMarkersLayout() {
        return waypointMarkersLayout;
    }

    public LLayerGroup getTemporaryLayout() {
        return temporaryLayout;
    }

    public LLayerGroup getPolygonsLayout() {
        return polygonsLayout;
    }

    public void setWaypointDraggingListener(IWayPointMarkerDragger listener) {
        this.waypointDraggingListener = listener;
    }


    public void setStateDraggingListener(IStateMarkerDragger listener) {
        this.stateDraggingListener = listener;
    }

    public double getWaypointParkingRadius() {
        return waypointParkingRadius;
    }

    public void addDeliveryMarker(LMarker marker, String type, String number, String status, String additionalInformation) {
        if (isDraggableWayPoints)
            marker.addDragEndListener(wayPointDragging);

            marker.setPopup(additionalInformation);

        waypointMarkersLayout.addComponent(marker);

        //Add way point parking radius to map
        if (showParkingRadius) {
            LCircle parkingCircle = new LCircle(marker.getPoint(), waypointParkingRadius);
            parkingCircle.setColor("green");
            factualTrackStatesLayout.addComponent(parkingCircle);
        }
    }

    public MenuBar.MenuItem addContextMenuItem(String itemCaption, MenuBar.Command command) {
        return contextMenu.addItem(itemCaption, command);
    }

    public void removeContextMenuItem(MenuBar.MenuItem item) {
        contextMenu.removeItem(item);
    }

    public void clearContextMenu() {
        contextMenu.removeItems();
    }

    public Point getCenterPoint(){
        return map.getCenter();
    }



}
