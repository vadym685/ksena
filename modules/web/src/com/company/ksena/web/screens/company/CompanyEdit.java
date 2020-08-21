package com.company.ksena.web.screens.company;

import com.company.ksena.entity.company.CompanyType;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.company.Company;

import javax.inject.Inject;
import java.util.Objects;

@UiController("ksena_Company.edit")
@UiDescriptor("company-edit.xml")
@EditedEntityContainer("companyDc")
@LoadDataBeforeShow
public class CompanyEdit extends StandardEditor<Company> {
    @Inject
    private TextField<String> nameField;
    @Inject
    private PickerField<CompanyType> companyTypeField;
    @Inject
    private TextField<String> fullNameField;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
       if (Objects.nonNull(nameField.getValue()) && Objects.nonNull(companyTypeField.getValue().getName())){
           fullNameField.setValue(nameField.getValue() + " " + companyTypeField.getValue().getName());
        }
    }
}