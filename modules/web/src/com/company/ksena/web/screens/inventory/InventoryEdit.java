package com.company.ksena.web.screens.inventory;

import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.inventory.Inventory;

import javax.inject.Inject;
import java.time.LocalDate;

@UiController("ksena_Inventory.edit")
@UiDescriptor("inventory-edit.xml")
@EditedEntityContainer("inventoryDc")
@LoadDataBeforeShow
public class InventoryEdit extends StandardEditor<Inventory> {
    @Inject
    private CheckBox visibleField;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        visibleField.setValue(true);
    }


}