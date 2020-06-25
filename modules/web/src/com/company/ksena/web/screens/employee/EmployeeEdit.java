package com.company.ksena.web.screens.employee;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.Employee;

@UiController("ksena_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
@LoadDataBeforeShow
public class EmployeeEdit extends StandardEditor<Employee> {
}