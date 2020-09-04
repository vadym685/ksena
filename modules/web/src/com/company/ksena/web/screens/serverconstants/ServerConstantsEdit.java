package com.company.ksena.web.screens.serverconstants;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.server_constants.ServerConstants;

@UiController("ksena_ServerConstants.edit")
@UiDescriptor("server-constants-edit.xml")
@EditedEntityContainer("serverConstantsDc")
@LoadDataBeforeShow
public class ServerConstantsEdit extends StandardEditor<ServerConstants> {
}