package com.company.ksena.web.screens.task;

import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.Task;

import javax.inject.Inject;

@UiController("ksena_Task.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
@LoadDataBeforeShow
public class TaskBrowse extends StandardLookup<Task> {
    @Inject
    private GroupTable<Task> tasksTable;

//    @Subscribe("tasksTable.copy")
//    public void onTasksTableCopy(Action.ActionPerformedEvent event) {
//        Task task = (Task) tasksTable.getSelected();
//    }
}