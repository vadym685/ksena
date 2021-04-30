package com.company.ksena.web.screens.task;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("ksena_CreateCompanyScreen")
@UiDescriptor("create-company-screen.xml")
@LoadDataBeforeShow
public class CreateCompanyScreen extends Screen {
    @Inject
    private TextField<String> companyNameField;
    @Inject
    private TextField<String> adressField;
    @Inject
    private TextField<String> contactField;

    @Subscribe("ok")
    public void onOkClick(Button.ClickEvent event) {
        close(StandardOutcome.CLOSE);
    }

    public String getCompanyName(){
        return companyNameField.getValue();
    }

    public String getAdress(){
        return adressField.getValue();
    }

    public String getContact(){
        return contactField.getValue();
    }
}