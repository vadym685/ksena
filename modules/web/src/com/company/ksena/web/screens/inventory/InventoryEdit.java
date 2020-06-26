package com.company.ksena.web.screens.inventory;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.inventory.Inventory;

@UiController("ksena_Inventory.edit")
@UiDescriptor("inventory-edit.xml")
@EditedEntityContainer("inventoryDc")
@LoadDataBeforeShow
public class InventoryEdit extends StandardEditor<Inventory> {
}