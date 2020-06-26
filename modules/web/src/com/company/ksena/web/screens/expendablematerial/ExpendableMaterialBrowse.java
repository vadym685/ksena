package com.company.ksena.web.screens.expendablematerial;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.inventory.ExpendableMaterial;

@UiController("ksena_ExpendableMaterial.browse")
@UiDescriptor("expendable-material-browse.xml")
@LookupComponent("expendableMaterialsTable")
@LoadDataBeforeShow
public class ExpendableMaterialBrowse extends StandardLookup<ExpendableMaterial> {
}