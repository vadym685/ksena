package com.company.ksena.web.utils.leaflet_map;

import com.company.ksena.entity.api.gps_gt_api.TrackMessage;
import com.company.ksena.entity.api.route_api.PlannedTrackPoint;
import com.company.ksena.web.utils.Constants;
import com.company.ksena.web.utils.Utils;
import com.company.ksena.web.utils.leaflet_map.entitys.MapPoint;
import org.vaadin.addon.leaflet.LPolyline;
import org.vaadin.addon.leaflet.jsonmodels.VectorStyle;
import org.vaadin.addon.leaflet.shared.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class Adapter {

    static final String FACTUAL_POLYLINE_COLOR = "#c71cc0";
    private static int FACTUAL_POLYLINE_WIGHT = 4;

    static final String PLANED_POLYLINE_COLOR = "#09aded";
    private static int PLANED_POLYLINE_WIGHT = 7;

    static LPolyline plannedTrackToPolyline(PlannedTrackPoint track) {

        ArrayList<Point> points = new ArrayList<>();

        for (String coord : track.getRoute_geometry()) {
            String[] c = coord.split(",");
            Point newPoint = new Point(Double.parseDouble(c[0]), Double.parseDouble(c[1]));
            points.add(newPoint);
        }

        LPolyline polyline = new LPolyline();
        polyline.setPoints(points);
        return polyline;
    }

    static LPolyline factualTrackToPolyline(List<TrackMessage> tracks, TimeZone timeZone) {
        ArrayList<Point> points = new ArrayList<>();

        for (TrackMessage coord : tracks) {
            MapPoint newPoint = new MapPoint(coord.lat, coord.lon);
            newPoint.setTime(Utils.unixToString(coord.t, Constants.TIME_H_M_S_FORMAT, timeZone));
            points.add(newPoint);
        }

        return polylineByPoints(points, FACTUAL_POLYLINE_COLOR);
    }


    static LPolyline polylineByPoints(List< Point> points, String lineColor) {

        String polylineColor = lineColor;
        if (!lineColor.contains("#")) {
            polylineColor = String.format("#%s", lineColor);
        }

        VectorStyle lineStyle = new VectorStyle();
        lineStyle.setColor(polylineColor);
        lineStyle.setWeight(FACTUAL_POLYLINE_WIGHT);

        LPolyline polyline = new LPolyline();
        polyline.setStyle(lineStyle);

        polyline.setPoints(points);
        return polyline;
    }


    public static LPolyline plannedTrackToPolyline(List<MapPoint> planedTrack) {

        return plannedTrackToPolyline(planedTrack, PLANED_POLYLINE_COLOR);
    }

    public static LPolyline plannedTrackToPolyline(List<MapPoint> planedTrack, String trackColor) {

        VectorStyle lineStyle = new VectorStyle();
        lineStyle.setColor(trackColor);
        lineStyle.setWeight(PLANED_POLYLINE_WIGHT);

        LPolyline polyline = new LPolyline();
        polyline.setStyle(lineStyle);

        ArrayList<Point> points = new ArrayList<Point>();
        points.addAll(planedTrack);
        polyline.setPoints(points);
        return polyline;
    }

    public static LPolyline plannedTrackToPolylineWithDottedLine(List<MapPoint> planedTrack, String trackColor) {

        VectorStyle lineStyle = new VectorStyle();
        lineStyle.setColor(trackColor);
        lineStyle.setWeight(PLANED_POLYLINE_WIGHT);
        lineStyle.setDashArray("20,10");
        lineStyle.setLineJoin("round");
        lineStyle.setLineCap("round");

        LPolyline polyline = new LPolyline();
        polyline.setStyle(lineStyle);

        ArrayList<Point> points = new ArrayList<Point>();
        points.addAll(planedTrack);
        polyline.setPoints(points);
        return polyline;
    }

    public static LPolyline plannedTrackToPolylineWithDottedLine(List<MapPoint> planedTrack) {
        return plannedTrackToPolylineWithDottedLine(planedTrack, PLANED_POLYLINE_COLOR);
    }
}
