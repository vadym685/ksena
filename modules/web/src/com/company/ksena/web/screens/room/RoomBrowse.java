package com.company.ksena.web.screens.room;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.cleaning_map.Room;
import com.vaadin.server.Page;
import org.springframework.lang.NonNull;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.UUID;


@UiController("ksena_Room.browse")
@UiDescriptor("room-browse.xml")
@LookupComponent("roomsTable")
@LoadDataBeforeShow
public class RoomBrowse extends StandardLookup<Room> {
    @Inject
    private GroupTable<Room> roomsTable;
    @Inject
    private CollectionContainer<Room> roomsDc;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        roomsDc.getItems().forEach(room -> {
            injectColorCss(room.getColor(), room.getId());
        });

        roomsTable.setStyleProvider(new GroupTable.StyleProvider<Room>() {
            @Nullable
            @Override
            public String getStyleName(@NonNull Room entity, @Nullable String property) {
                if (property != null && property.equals("color")) {
                    return "colored-cell-" + entity.getId() + "-" + entity.getColor();
                }
                return null;
            }
        });
    }

    private void injectColorCss(String color, UUID id) {
        Page.Styles styles = Page.getCurrent().getStyles();
        if (color.contains("#")) {
            color = color.replace("#", "");
        }
        styles.add(String.format(
                ".colored-cell-%s-%s{background-color:#%s;}",
                id.toString(), color, color));
    }


    @Subscribe(id = "roomsDc", target = Target.DATA_CONTAINER)
    public void onRoomsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Room> event) {

        injectColorCss(event.getItem().getColor(), event.getItem().getId());

    }

    @Subscribe(id = "roomsDc", target = Target.DATA_CONTAINER)
    public void onRoomsDcItemChange(InstanceContainer.ItemChangeEvent<Room> event) {
        if (event.getItem() != null) {
            injectColorCss(event.getItem().getColor(), event.getItem().getId());

        }
    }

    public Room getSelectedRoom(){
        return roomsTable.getSingleSelected();
    }

}