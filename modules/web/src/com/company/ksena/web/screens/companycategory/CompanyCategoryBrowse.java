package com.company.ksena.web.screens.companycategory;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.company.CompanyCategory;

@UiController("ksena_CompanyCategory.browse")
@UiDescriptor("company-category-browse.xml")
@LookupComponent("companyCategoriesTable")
@LoadDataBeforeShow
public class CompanyCategoryBrowse extends StandardLookup<CompanyCategory> {
}