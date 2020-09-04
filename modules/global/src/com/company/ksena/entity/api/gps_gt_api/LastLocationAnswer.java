package com.company.ksena.entity.api.gps_gt_api;

public class LastLocationAnswer extends TrackMessage {
    public long globalID = 0L;

    public long getGlobalID() {
        return globalID;
    }

    public void setGlobalID(long globalID) {
        this.globalID = globalID;
    }
}