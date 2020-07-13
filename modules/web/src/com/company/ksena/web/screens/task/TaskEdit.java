package com.company.ksena.web.screens.task;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.Task;

@UiController("ksena_Task.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
@LoadDataBeforeShow
public class TaskEdit extends StandardEditor<Task> {
}