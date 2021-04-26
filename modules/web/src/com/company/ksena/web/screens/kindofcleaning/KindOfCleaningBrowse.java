package com.company.ksena.web.screens.kindofcleaning;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.KindOfCleaning;

@UiController("ksena_KindOfCleaning.browse")
@UiDescriptor("kind-of-cleaning-browse.xml")
@LookupComponent("kindOfCleaningsTable")
@LoadDataBeforeShow
public class KindOfCleaningBrowse extends StandardLookup<KindOfCleaning> {
}