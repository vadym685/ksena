package com.company.ksena.web.screens.task;

import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.Task;

@UiController("ksena_Task.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
@LoadDataBeforeShow
public class TaskBrowse extends StandardLookup<Task> {
    @Subscribe("tasksTable.copy")
    public void onTasksTableCopy(Action.ActionPerformedEvent event) {
        event.getComponent();
    }
}