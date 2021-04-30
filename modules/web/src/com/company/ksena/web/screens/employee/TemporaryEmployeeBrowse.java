package com.company.ksena.web.screens.employee;

import com.company.ksena.entity.people.Employee;
import com.company.ksena.entity.people.EmployeeType;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("ksena_TemporaryEmployeeBrowse.browse")
@UiDescriptor("temporary-employee-browse.xml")
@LookupComponent("employeesTable")
@LoadDataBeforeShow
public class TemporaryEmployeeBrowse extends StandardLookup<Employee> {
    @Inject
    private CollectionLoader<Employee> employeesDl;

    @Subscribe
    public void onInit(InitEvent event) {
        employeesDl.setParameter("employeeType", EmployeeType.TEMPORARY_STAFF);
        employeesDl.load();
    }
    @Inject
    private GroupTable<Employee> employeesTable;

    public Employee getSelectedEmployee(){
        return employeesTable.getSingleSelected();
    }
}