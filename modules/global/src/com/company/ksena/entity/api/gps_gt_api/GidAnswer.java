package com.company.ksena.entity.api.gps_gt_api;

import java.io.Serializable;

public class GidAnswer implements Serializable {
    public long globalID = 0L;

    public long getGlobalID() {
        return globalID;
    }

    public void setGlobalID(long globalID) {
        this.globalID = globalID;
    }
}