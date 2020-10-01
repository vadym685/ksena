package com.company.ksena.web.screens.inventory;

import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.inventory.Inventory;

import javax.inject.Inject;

@UiController("ksena_Inventory.browse")
@UiDescriptor("inventory-browse.xml")
@LookupComponent("inventoriesTable")
@LoadDataBeforeShow
public class InventoryBrowse extends StandardLookup<Inventory> {
    @Inject
    private GroupTable<Inventory> inventoriesTable;

    public Inventory getSelectedInventory(){
        return inventoriesTable.getSingleSelected();
    }
}