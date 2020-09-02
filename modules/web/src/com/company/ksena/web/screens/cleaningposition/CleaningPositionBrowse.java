package com.company.ksena.web.screens.cleaningposition;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.cleaning_map.CleaningPosition;

@UiController("ksena_CleaningPosition.browse")
@UiDescriptor("cleaning-position-browse.xml")
@LookupComponent("cleaningPositionsTable")
@LoadDataBeforeShow
public class CleaningPositionBrowse extends StandardLookup<CleaningPosition> {
    @Subscribe
    public void onAfterClose(AfterCloseEvent event) {
        
    }
    
}