package com.company.ksena.web.screens.fieldofactivity;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.company.FieldOfActivity;

@UiController("ksena_FieldOfActivity.edit")
@UiDescriptor("field-of-activity-edit.xml")
@EditedEntityContainer("fieldOfActivityDc")
@LoadDataBeforeShow
public class FieldOfActivityEdit extends StandardEditor<FieldOfActivity> {
}