package com.company.ksena.web.utils;

public class Constants {

    public static final String TASK_STATUS_COMPLETED = "EXECUTED";
    public static final String TASK_STATUS_REJECTED = "REJECTED";

    public static final String FIRST_CAR_POSITION_MARKER = "FIRST_CAR_POSITION_MARKER";
    public static final String STOP_MARKER = "START_MARKER";
    public static final String END_MARKER = "END_MARKER";
    public static final String WAREHOUSE_MARKER = "WAREHOUSE_MARKER";
    public static final String WAYPOINT_MARKER = "WAYPOINT_MARKER";
    public static final String LAST_CAR_POSITION_MARKER = "LAST_CAR_POSITION_MARKER";

    public static final String MONITORING_SETTINGS = "MONITORING_SETTINGS";
    public static final String ONE_S_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String ONE_S_DATE_FORMAT = "yyyy-MM-dd";
    public static final String ONE_S_TIME_FORMAT = "HH:mm:ss";
    public static final String ENTITY_DATE_FORMAT = "yyyyMMdd";
    public static final String TIME_H_M_S_FORMAT = "HH:mm:ss";
    public static final String TIME_H_M_FORMAT = "HH:mm";
    public static final int NOT_HAVE_FACT = -3;


    public static String WAREHOUSE_ID = "-1";
    public static String POINT_SERVICE_TIME = "POINT_SERVICE_TIME";

    //Путь к ресурсам
    public static String PNG_TRANSPORTS_ICONS_PATH = "VAADIN/images/map_markers/png/transports/";
    public static String PNG_POINTS_ICONS_PATH = "VAADIN/images/map_markers/png/points/";

    public static String SVG_TRANSPORTS_ICONS_PATH = "VAADIN/images/map_markers/svg/transports/";
    public static String SVG_POINTS_ICONS_PATH = "VAADIN/images/map_markers/svg/points/";


    public static String MARKER_WITH_TEXT_FORMAT = "<div class=\"container4\"><p class=\"container4_p  %s\"> %s </p></div>";
    public static String MARKER_WITH_COLORED_TEXT_FORMAT = "<div class=\"container4\"><p class=\"container4_p\" %s> %s </p></div>";
    public static String IMAGE_MARKER_FORMAT = "<img src=\" %s \">";
    public static String IMAGE_MARKER_WITH_TEXT_FORMAT = IMAGE_MARKER_FORMAT + MARKER_WITH_TEXT_FORMAT;
    public static String IMAGE_MARKER_WITH_COLORED_TEXT_FORMAT = IMAGE_MARKER_FORMAT + MARKER_WITH_COLORED_TEXT_FORMAT;


}
