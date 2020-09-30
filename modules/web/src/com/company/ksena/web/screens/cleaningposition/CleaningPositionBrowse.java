package com.company.ksena.web.screens.cleaningposition;

import com.company.ksena.entity.cleaning_map.Room;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.cleaning_map.CleaningPosition;

import javax.inject.Inject;

@UiController("ksena_CleaningPosition.browse")
@UiDescriptor("cleaning-position-browse.xml")
@LookupComponent("cleaningPositionsTable")
@LoadDataBeforeShow
public class CleaningPositionBrowse extends StandardLookup<CleaningPosition> {
    @Inject
    private GroupTable<CleaningPosition> cleaningPositionsTable;


    public CleaningPosition getSelectedCleaningPosition(){
        return cleaningPositionsTable.getSingleSelected();
    }

}