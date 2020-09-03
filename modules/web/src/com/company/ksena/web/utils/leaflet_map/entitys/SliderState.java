package com.company.ksena.web.utils.leaflet_map.entitys;

import com.vaadin.shared.ui.JavaScriptComponentState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SliderState extends JavaScriptComponentState {
    private Logger LOG = LoggerFactory.getLogger(SliderState.class);

    private SliderSettings settings = new SliderSettings();

    public void setSettings(SliderSettings settings) {
        this.settings = settings;
    }
}
