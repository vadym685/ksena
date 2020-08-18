package com.company.ksena.web.screens.clientemployee;

import com.haulmont.cuba.gui.components.MaskedField;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.ClientEmployee;

import javax.inject.Inject;
import java.util.Objects;

@UiController("ksena_ClientEmployee.edit")
@UiDescriptor("client-employee-edit.xml")
@EditedEntityContainer("clientEmployeeDc")
@LoadDataBeforeShow
public class ClientEmployeeEdit extends StandardEditor<ClientEmployee> {

    @Inject
    private MaskedField<String> phoneNumberField;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {

        if (phoneNumberField.getValue() == null) {
            phoneNumberField.setValue("+420");
        }
        ;
    }
}