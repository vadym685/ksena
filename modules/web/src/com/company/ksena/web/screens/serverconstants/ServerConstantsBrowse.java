package com.company.ksena.web.screens.serverconstants;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.server_constants.ServerConstants;

@UiController("ksena_ServerConstants.browse")
@UiDescriptor("server-constants-browse.xml")
@LookupComponent("serverConstantsesTable")
@LoadDataBeforeShow
public class ServerConstantsBrowse extends StandardLookup<ServerConstants> {
}