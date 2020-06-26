package com.company.ksena.web.screens.cleaningposition;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.cleaning_map.CleaningPosition;

@UiController("ksena_CleaningPosition.edit")
@UiDescriptor("cleaning-position-edit.xml")
@EditedEntityContainer("cleaningPositionDc")
@LoadDataBeforeShow
public class CleaningPositionEdit extends StandardEditor<CleaningPosition> {
}