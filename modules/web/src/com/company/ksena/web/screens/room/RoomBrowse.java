package com.company.ksena.web.screens.room;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.cleaning_map.Room;

@UiController("ksena_Room.browse")
@UiDescriptor("room-browse.xml")
@LookupComponent("roomsTable")
@LoadDataBeforeShow
public class RoomBrowse extends StandardLookup<Room> {
}