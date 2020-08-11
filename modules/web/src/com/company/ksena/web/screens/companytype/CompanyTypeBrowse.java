package com.company.ksena.web.screens.companytype;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.company.CompanyType;

@UiController("ksena_CompanyType.browse")
@UiDescriptor("company-type-browse.xml")
@LookupComponent("companyTypesTable")
@LoadDataBeforeShow
public class CompanyTypeBrowse extends StandardLookup<CompanyType> {
}