package com.company.ksena.web.screens.inventorywrapper;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.inventory.InventoryWrapper;

@UiController("ksena_InventoryWrapper.edit")
@UiDescriptor("inventory-wrapper-edit.xml")
@EditedEntityContainer("inventoryWrapperDc")
@LoadDataBeforeShow
public class InventoryWrapperEdit extends StandardEditor<InventoryWrapper> {
}