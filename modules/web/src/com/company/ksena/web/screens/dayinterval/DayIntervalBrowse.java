package com.company.ksena.web.screens.dayinterval;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.DayInterval;

@UiController("ksena_DayInterval.browse")
@UiDescriptor("day-interval-browse.xml")
@LookupComponent("dayIntervalsTable")
@LoadDataBeforeShow
public class DayIntervalBrowse extends StandardLookup<DayInterval> {
}