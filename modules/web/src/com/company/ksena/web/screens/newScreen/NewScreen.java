package com.company.ksena.web.screens.newScreen;

import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.company.CompanyCategory;
import com.company.ksena.entity.company.CompanyType;
import com.company.ksena.entity.people.ClientEmployee;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@UiController("ksena_NewScreen")
@UiDescriptor("new-screen.xml")
public class NewScreen extends Screen {
    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;

    @Subscribe("createCompanyBond")
    public void oncreateCompanyBondClick(Button.ClickEvent event) {
        List companyList = dataManager.load(Company.class)
                .query("select e from ksena_Company e")
                .view("company-view")
                .list();
        for (Object company : companyList){

            CommitContext commitContext = new CommitContext();

            Company thisCompany = (Company)company;
            String companyTypeName = thisCompany.getTempCompanyType();
            String companyCaregoryName = thisCompany.getTempCompanyCategory();
            if (companyTypeName != null) {
                try {
                    CompanyType companyType = dataManager.load(CompanyType.class)
                            .query("select e from ksena_CompanyType e where e.name = :name")
                            .view("companyType-view")
                            .parameter("name", companyTypeName)
                            .one();
                    thisCompany.setCompanyType(companyType);
                } catch (Exception e) {
                    CompanyType newCompanyType = metadata.create(CompanyType.class);
                    newCompanyType.setName(companyTypeName);
                    commitContext.addInstanceToCommit(newCompanyType);
                    thisCompany.setCompanyType(newCompanyType);
                }

            }
            if (companyCaregoryName != null) {
                try {
                    CompanyCategory companyCategory = dataManager.load(CompanyCategory.class)
                            .query("select e from ksena_CompanyCategory e where e.name = :name")
                            .view("_local")
                            .parameter("name", companyCaregoryName)
                            .one();
                    thisCompany.setCompanyCategory(companyCategory);
                } catch (Exception e) {
                    CompanyCategory newCompanyCategory = metadata.create(CompanyCategory.class);
                    newCompanyCategory.setName(companyCaregoryName);
                    commitContext.addInstanceToCommit(newCompanyCategory);
                    thisCompany.setCompanyCategory(newCompanyCategory);
                }

            }
            commitContext.addInstanceToCommit(thisCompany);
            dataManager.commit(commitContext);
        }

    }

    @Subscribe("createEmploeeBond")
    public void onCreateEmploeeBondClick(Button.ClickEvent event) {
        List clientEmployeeList = dataManager.load(ClientEmployee.class)
                .query("select e from ksena_ClientEmployee e")
                .view("clientEmployee-view")
                .list();
        for (Object clientEmployee : clientEmployeeList){

            CommitContext commitContext = new CommitContext();

            ClientEmployee thisClientEmployee = (ClientEmployee) clientEmployee;
            String companyName = thisClientEmployee.getTempCompanyName();
            if (companyName != null) {
                try {
                    Company company = dataManager.load(Company.class)
                            .query("select e from ksena_Company e where e.name = :name")
                            .view("company-view")
                            .parameter("name", companyName)
                            .one();
                    thisClientEmployee.setCompanies(company);
                } catch (Exception e) {
                }
            }
            commitContext.addInstanceToCommit(thisClientEmployee);
            dataManager.commit(commitContext);
        }
    }

}