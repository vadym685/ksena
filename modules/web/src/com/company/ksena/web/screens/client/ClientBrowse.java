package com.company.ksena.web.screens.client;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.Client;

@UiController("ksena_Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
@LoadDataBeforeShow
public class ClientBrowse extends StandardLookup<Client> {
}