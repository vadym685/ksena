package com.company.ksena.web.screens.employee;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.Employee;

@UiController("ksena_Employee.browse")
@UiDescriptor("employee-browse.xml")
@LookupComponent("employeesTable")
@LoadDataBeforeShow
public class EmployeeBrowse extends StandardLookup<Employee> {
}