package com.company.ksena.web.screens.company;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.company.Company;

@UiController("ksena_Company.edit")
@UiDescriptor("company-edit.xml")
@EditedEntityContainer("companyDc")
@LoadDataBeforeShow
public class CompanyEdit extends StandardEditor<Company> {
}