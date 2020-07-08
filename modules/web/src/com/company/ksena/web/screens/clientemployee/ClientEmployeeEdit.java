package com.company.ksena.web.screens.clientemployee;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.ClientEmployee;

@UiController("ksena_ClientEmployee.edit")
@UiDescriptor("client-employee-edit.xml")
@EditedEntityContainer("clientEmployeeDc")
@LoadDataBeforeShow
public class ClientEmployeeEdit extends StandardEditor<ClientEmployee> {
}