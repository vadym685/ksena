package com.company.ksena.web.utils.leaflet_map;

import java.awt.*;


class MarkerSettings {
    private double xAnchorShift = 0.0;
    private double yAnchorShift = 0.0;
    private double iconWidth = 32.0;
    private double iconHeight = 32.0;
    private Color mainColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;

    public double getxAnchorShift() {
        return xAnchorShift;
    }

    public void setxAnchorShift(double xAnchorShift) {
        this.xAnchorShift = xAnchorShift;
    }

    public double getyAnchorShift() {
        return yAnchorShift;
    }

    public void setyAnchorShift(double yAnchorShift) {
        this.yAnchorShift = yAnchorShift;
    }

    public double getIconWidth() {
        return iconWidth;
    }

    public void setIconWidth(double iconWidth) {
        this.iconWidth = iconWidth;
    }

    public double getIconHeight() {
        return iconHeight;
    }

    public void setIconHeight(double iconHeight) {
        this.iconHeight = iconHeight;
    }

    public Color getMainColor() {
        return mainColor;
    }

    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
