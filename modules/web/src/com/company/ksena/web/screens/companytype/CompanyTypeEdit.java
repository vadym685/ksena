package com.company.ksena.web.screens.companytype;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.company.CompanyType;

@UiController("ksena_CompanyType.edit")
@UiDescriptor("company-type-edit.xml")
@EditedEntityContainer("companyTypeDc")
@LoadDataBeforeShow
public class CompanyTypeEdit extends StandardEditor<CompanyType> {
}