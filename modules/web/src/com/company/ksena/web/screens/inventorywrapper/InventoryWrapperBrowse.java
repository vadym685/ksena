package com.company.ksena.web.screens.inventorywrapper;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.inventory.InventoryWrapper;

@UiController("ksena_InventoryWrapper.browse")
@UiDescriptor("inventory-wrapper-browse.xml")
@LookupComponent("inventoryWrappersTable")
@LoadDataBeforeShow
public class InventoryWrapperBrowse extends StandardLookup<InventoryWrapper> {
}