package com.company.ksena.web.screens.companycategory;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.company.CompanyCategory;

@UiController("ksena_CompanyCategory.edit")
@UiDescriptor("company-category-edit.xml")
@EditedEntityContainer("companyCategoryDc")
@LoadDataBeforeShow
public class CompanyCategoryEdit extends StandardEditor<CompanyCategory> {
}