package com.company.ksena.web.screens.client;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.Client;

@UiController("ksena_Client.edit")
@UiDescriptor("client-edit.xml")
@EditedEntityContainer("clientDc")
@LoadDataBeforeShow
public class ClientEdit extends StandardEditor<Client> {
}