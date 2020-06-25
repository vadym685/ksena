package com.company.ksena.web.screens.inventory;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.Inventory;

@UiController("ksena_Inventory.browse")
@UiDescriptor("inventory-browse.xml")
@LookupComponent("inventoriesTable")
@LoadDataBeforeShow
public class InventoryBrowse extends StandardLookup<Inventory> {
}