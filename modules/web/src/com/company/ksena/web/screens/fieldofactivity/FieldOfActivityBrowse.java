package com.company.ksena.web.screens.fieldofactivity;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.company.FieldOfActivity;

@UiController("ksena_FieldOfActivity.browse")
@UiDescriptor("field-of-activity-browse.xml")
@LookupComponent("fieldOfActivitiesTable")
@LoadDataBeforeShow
public class FieldOfActivityBrowse extends StandardLookup<FieldOfActivity> {
}