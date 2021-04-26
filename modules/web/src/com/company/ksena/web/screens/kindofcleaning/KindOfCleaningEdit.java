package com.company.ksena.web.screens.kindofcleaning;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.KindOfCleaning;

@UiController("ksena_KindOfCleaning.edit")
@UiDescriptor("kind-of-cleaning-edit.xml")
@EditedEntityContainer("kindOfCleaningDc")
@LoadDataBeforeShow
public class KindOfCleaningEdit extends StandardEditor<KindOfCleaning> {
}