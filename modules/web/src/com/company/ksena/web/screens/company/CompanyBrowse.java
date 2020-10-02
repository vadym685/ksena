package com.company.ksena.web.screens.company;

import com.company.ksena.entity.people.ClientEmployee;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.company.Company;

@UiController("ksena_Company.browse")
@UiDescriptor("company-browse.xml")
@LookupComponent("companiesTable")
@LoadDataBeforeShow
public class CompanyBrowse extends StandardLookup<Company> {

}