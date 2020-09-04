package com.company.ksena.web.utils.leaflet_map.entitys;

import java.util.ArrayList;

public class SliderSettings {
    private Long min = 0L;
    private Long max = 0L;
    private ArrayList<Long> range = new ArrayList<>();
    private String incorrectMsg = "Date {0} is incorrect!";

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public ArrayList<Long> getRange() {
        return range;
    }

    public void setRange(ArrayList<Long> range) {
        this.range = range;
    }

    public String getIncorrectMsg() {
        return incorrectMsg;
    }

    public void setIncorrectMsg(String incorrectMsg) {
        this.incorrectMsg = incorrectMsg;
    }
}
