package com.company.ksena.web.screens.clientemployee;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.ClientEmployee;

@UiController("ksena_ClientEmployee.browse")
@UiDescriptor("client-employee-browse.xml")
@LookupComponent("clientEmployeesTable")
@LoadDataBeforeShow
public class ClientEmployeeBrowse extends StandardLookup<ClientEmployee> {
}