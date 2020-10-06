package com.company.ksena.web.screens.clientemployee;

import com.company.ksena.entity.company.Company;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.ClientEmployee;

import javax.inject.Inject;

@UiController("ksena_ClientEmployeeWithCompany.browse")
@UiDescriptor("client-employeeWithCompany-browse.xml")
@LookupComponent("clientEmployeesTable")
@LoadDataBeforeShow
public class ClientEmployeeWithCompanyBrowse extends StandardLookup<ClientEmployee> {
    @Inject
    private GroupTable<ClientEmployee> clientEmployeesTable;

    @Inject
    private CollectionLoader<ClientEmployee> clientEmployeesDl;
    @Inject
    private DataManager dataManager;

    public ClientEmployee getSelectedClientEmployee(){
        return clientEmployeesTable.getSingleSelected();
    }

    public void setQueryParametr(Company company){

      clientEmployeesDl.setParameter("companies", company);

    }
}