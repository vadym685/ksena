package com.company.ksena.web.screens.positionwrapper;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.cleaning_map.PositionWrapper;

@UiController("ksena_PositionWrapper.browse")
@UiDescriptor("position-wrapper-browse.xml")
@LookupComponent("positionWrappersTable")
@LoadDataBeforeShow
public class PositionWrapperBrowse extends StandardLookup<PositionWrapper> {
}