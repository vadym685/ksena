package com.company.ksena.web.screens.positionwrapper;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.cleaning_map.PositionWrapper;

@UiController("ksena_PositionWrapper.edit")
@UiDescriptor("position-wrapper-edit.xml")
@EditedEntityContainer("positionWrapperDc")
@LoadDataBeforeShow
public class PositionWrapperEdit extends StandardEditor<PositionWrapper> {
}