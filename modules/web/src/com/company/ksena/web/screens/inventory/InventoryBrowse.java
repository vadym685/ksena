package com.company.ksena.web.screens.inventory;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
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
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<Inventory> inventoriesDl;

    public Inventory getSelectedInventory(){
        return inventoriesTable.getSingleSelected();
    }

    @Subscribe(id = "inventoriesDc", target = Target.DATA_CONTAINER)
    public void inventoriesDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Inventory> event) {
        dataManager.commit(event.getItem());
        inventoriesDl.load();
    }

}