package com.company.ksena.web.screens.cleaningposition;

import com.company.ksena.entity.cleaning_map.Room;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.vaadin.server.Page;
import org.springframework.lang.NonNull;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.UUID;

@UiController("ksena_CleaningPosition.browse")
@UiDescriptor("cleaning-position-browse.xml")
@LookupComponent("cleaningPositionsTable")
@LoadDataBeforeShow
public class CleaningPositionBrowse extends StandardLookup<CleaningPosition> {
    @Inject
    private GroupTable<CleaningPosition> cleaningPositionsTable;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<CleaningPosition> cleaningPositionsDl;
    @Inject
    private CollectionContainer<CleaningPosition> cleaningPositionsDc;


    public CleaningPosition getSelectedCleaningPosition(){
        return cleaningPositionsTable.getSingleSelected();
    }

    @Subscribe(id = "cleaningPositionsDc", target = Target.DATA_CONTAINER)
    public void onCleaningPositionsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<CleaningPosition> event) {
        dataManager.commit(event.getItem());
        cleaningPositionsDl.load();
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        cleaningPositionsDc.getItems().forEach(cleaningPosition -> {
            injectColorCss(cleaningPosition.getRoom().getColor(), cleaningPosition.getId());
        });

        cleaningPositionsTable.setStyleProvider(new GroupTable.StyleProvider<CleaningPosition>() {
            @Nullable
            @Override
            public String getStyleName(CleaningPosition entity, @Nullable String property) {
                if (property != null && property.equals("room")) {
                    return "colored-cell-" + entity.getId() + "-" + entity.getRoom().getColor();
                }
                return null;
            }
        });
    }

    private void injectColorCss(String color, UUID id) {
        Page.Styles styles = Page.getCurrent().getStyles();
        if (color != null){
            if (color.contains("#")) {
                color = color.replace("#", "");
            }
            styles.add(String.format(
                    ".colored-cell-%s-%s{background-color:#%s;}",
                    id.toString(), color, color));
        }}


    @Subscribe(id = "cleaningPositionsDc", target = Target.DATA_CONTAINER)
    public void cleaningPositionsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<CleaningPosition> event) {

        injectColorCss(event.getItem().getRoom().getColor(), event.getItem().getId());

    }

    @Subscribe(id = "cleaningPositionsDc", target = Target.DATA_CONTAINER)
    public void cleaningPositionsDcItemChange(InstanceContainer.ItemChangeEvent<CleaningPosition> event) {
        if (event.getItem() != null) {
            injectColorCss(event.getItem().getRoom().getColor(), event.getItem().getId());

        }
    }

}