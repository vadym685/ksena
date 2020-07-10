package com.company.ksena.web.screens.breakage;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.inventory.Breakage;

@UiController("ksena_Breakage.edit")
@UiDescriptor("breakage-edit.xml")
@EditedEntityContainer("breakageDc")
@LoadDataBeforeShow
public class BreakageEdit extends StandardEditor<Breakage> {
}