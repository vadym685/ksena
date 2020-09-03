package com.company.ksena.service;

import com.company.ksena.entity.point.Point;
import com.company.ksena.entity.server_constants.ServerConstants;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;

import java.util.List;
import java.util.UUID;

public interface RepoService {
    String NAME = "erp_RepoService";

    Point loadWayPointByExtraID(String pointID);

    Point loadWayPointByWialonID(String pointID);

    void saveData(StandardEntity item);

    void reload(StandardEntity item, String viewName);

    void removeData(StandardEntity newItem);

    ServerConstants getServerConstants();

    List<Point> loadAllWayPoints();

    List<Point> loadAllWayPointsInBounds(double southWestLng, double southWestLat, double northEastLng, double northEastLat);

    Point loadWaypointByWialonIdFromDB(String wialonId);

    Point loadWaypointFromDB(UUID id);

    FileDescriptor getFileDescriptorByFileName(String fileName);

}