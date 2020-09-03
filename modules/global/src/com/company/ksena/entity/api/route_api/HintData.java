package com.company.ksena.entity.api.route_api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HintData implements Serializable {

    public long checksum  = 0L;
    public List<String> locations = new ArrayList<>();

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}