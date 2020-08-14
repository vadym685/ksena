package com.company.ksena.web.screens.room;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.cleaning_map.Room;

@UiController("ksena_Room.edit")
@UiDescriptor("room-edit.xml")
@EditedEntityContainer("roomDc")
@LoadDataBeforeShow
public class RoomEdit extends StandardEditor<Room> {
}