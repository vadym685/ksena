package com.company.ksena.web.screens.expendablematerial;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.inventory.ExpendableMaterial;

@UiController("ksena_ExpendableMaterial.edit")
@UiDescriptor("expendable-material-edit.xml")
@EditedEntityContainer("expendableMaterialDc")
@LoadDataBeforeShow
public class ExpendableMaterialEdit extends StandardEditor<ExpendableMaterial> {
}