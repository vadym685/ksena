package com.company.ksena.web.screens.task;

import com.company.ksena.entity.task.Task;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionChangeType;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import groovy.util.ObservableList;
import groovy.util.ObservableMap;
import net.bull.javamelody.internal.common.LOG;

import javax.inject.Inject;

@UiController("ksena_GeneralTask.browse")
@UiDescriptor("general_task-browse.xml")
@LookupComponent("tasksTable")
@LoadDataBeforeShow
public class GeneralTaskBrowse extends StandardLookup<Task> {
    @Inject
    private GroupTable<Task> tasksTable;
    @Inject
    private DataManager dataManager;

    @Subscribe(id = "tasksDc", target = Target.DATA_CONTAINER)
    public void onTasksDcCollectionChange(CollectionContainer.CollectionChangeEvent<Task> event) {
        if (event.getChangeType() == CollectionChangeType.ADD_ITEMS) {
            CommitContext cc = new CommitContext();

            for (Task task : event.getChanges()) {
                task.setDisposable(true);
                cc.addInstanceToCommit(task);
            }
            dataManager.commit(cc);
        }


    }


//    @Subscribe("tasksTable.copy")
//    public void onTasksTableCopy(Action.ActionPerformedEvent event) {
//        Task task = (Task) tasksTable.getSelected();
//    }
}