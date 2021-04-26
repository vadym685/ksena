package com.company.ksena.web.screens.employee;

import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.Employee;

import javax.inject.Inject;

@UiController("ksena_Employee.browse")
@UiDescriptor("employee-browse.xml")
@LookupComponent("employeesTable")
@LoadDataBeforeShow
public class EmployeeBrowse extends StandardLookup<Employee> {
    @Inject
    private GroupTable<Employee> employeesTable;

    public Employee getSelectedEmployee(){
        return employeesTable.getSingleSelected();
    }
}