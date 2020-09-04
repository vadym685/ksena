package com.company.ksena.entity.api.gps_gt_api;

public enum GPS_STATE {
    NO_SIGNAL(4),
    START(3),
    ARRIVAL(2),
    UNKNOWN(1),
    MOVE(0),
    ALL(-1);

    private int value;

    GPS_STATE(int id) {
        this.value = id;
    }

    public int getValue() {
        return value;
    }
}