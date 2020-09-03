package com.company.ksena.service;

import com.company.ksena.entity.point.Point;
import com.company.ksena.entity.server_constants.ServerConstants;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Service(RepoService.NAME)
public class RepoServiceBean implements RepoService {

    private Logger LOG = LoggerFactory.getLogger(RepoService.class);

    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;

    public List<Group> loadGroupsForUserGroup(User user) {
        try {
            return dataManager.load(Group.class)
                    .query("select item from sec$Group item where item.parent = :parent")
                    .parameter("parent", user.getGroup())
                    .list();

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Group> loadGroupsForBaseGroup(Group baseGroup) {
        try {
            return dataManager.load(Group.class)
                    .query("select item from sec$Group item where item.parent = :parent")
                    .parameter("parent", baseGroup)
                    .list();

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Group> loadAllGroups() {
        try {
            return dataManager.load(Group.class)
                    .query("select item from sec$Group p")
                    .list();

        } catch (Exception ex) {
            return null;
        }
    }

    public Point loadWayPointByExtraID(String wayPointID) {
        try {
            return dataManager.load(Point.class)
                    .query("select item from erp_WayPoint item where item.extraId = :extraId")
                    .parameter("extraId", wayPointID)
                    .view("wayPoint-loader")
                    .one();

        } catch (Exception ex) {
            return null;
        }
    }


    public Point loadWayPointByWialonID(String wayPointID) {
        try {
            return dataManager.load(Point.class)
                    .query("select item from erp_WayPoint item where item.wialonId = :wialonId")
                    .parameter("wialonId", wayPointID)
                    .view("wayPoint-loader")
                    .one();

        } catch (Exception ex) {
            return null;
        }
    }

    public void saveData(StandardEntity item) {
        dataManager.commit(item);
    }


    public void reload(StandardEntity item, String viewName) {
        dataManager.reload(item, viewName);
    }


    public void removeData(StandardEntity newItem) {
        dataManager.remove(newItem);
    }

    public ServerConstants getServerConstants() {

        ServerConstants constants = null;
        try {
            constants = dataManager.load(ServerConstants.class)
                    .query("select item from ksena_ServerConstants item")
                    .one();
        } catch (Exception ex) {

        }

        if (constants == null) {
            constants = metadata.create(ServerConstants.class);
            saveData(constants);
        }

        return constants;
    }

    public List<Point> loadAllWayPoints() {
        try {
            return dataManager.load(Point.class)
                    .query("select item from erp_WayPoint item")
                    .view("wayPoint-loader")
                    .list();

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Point> loadAllWayPointsInBounds(double southWestLng, double southWestLat, double northEastLng, double northEastLat) {
        try {
            return dataManager.load(Point.class)
                    .query("select item from erp_WayPoint item where " +
                            "(item.lat between :southWestLat and :northEastLat) and " +
                            "(item.lon between :southWestLon and :northEastLon)")
                    .parameter("southWestLat", southWestLat)
                    .parameter("southWestLon", southWestLng)
                    .parameter("northEastLat", northEastLat)
                    .parameter("northEastLon", northEastLng)
                    .view("wayPoint-loader")
                    .list();
        } catch (Exception e) {
            return null;
        }
    }

    public Point loadWaypointByWialonIdFromDB(String wialonId) {
        try {
            return dataManager.load(Point.class)
                    .query("select item from erp_WayPoint item where item.wialonId = :wialonId")
                    .parameter("wialonId", wialonId)
                    .view("wayPoint-loader")
                    .one();

        } catch (Exception ex) {
            return null;
        }
    }

    public Point loadWaypointFromDB(UUID id) {
        try {
            return dataManager.load(Point.class)
                    .query("select item from erp_WayPoint item where item.id = :id")
                    .parameter("id", id)
                    .view("wayPoint-loader")
                    .one();

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public FileDescriptor getFileDescriptorByFileName(String fileName) {
        try {

            return dataManager.load(FileDescriptor.class)
                    .query("select item from sys$FileDescriptor item where item.name = :fileName")
                    .parameter("fileName", fileName)
                    .one();

        } catch (Exception ex) {
            return null;
        }
    }

}