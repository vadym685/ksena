package com.company.ksena.web.screens.task;

import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.task.TaskDocument;
import com.company.ksena.entity.task.TaskStatus;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.constraints.Null;
import java.util.Objects;

@UiController("ksena_Task.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
@LoadDataBeforeShow
public class TaskEdit extends StandardEditor<Task> {
    @Inject
    private LookupField<TaskStatus> taskStatusField;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (Objects.isNull(taskStatusField.getValue())) {
            taskStatusField.setValue(TaskStatus.CREATE);
        }
    }

    private final Logger LOG = LoggerFactory.getLogger(TaskEdit.class);

    @Inject
    private PickerField<Company> companyField;

    @Subscribe("taskDocumentField")
    public void onTaskDocumentFieldValueChange1(HasValue.ValueChangeEvent<TaskDocument> event) {
        LOG.info("");
        TaskDocument document  = event.getValue();
        if(document!=null){
            getEditedEntity().setPoint(document.getPoint());
            getEditedEntity().setCompany(document.getCompany());
            getEditedEntity().setCleaningMap(document.getCleaningMap());
            getEditedEntity().setInventory(document.getInventory());
        } else {
            getEditedEntity().setPoint(null);
            getEditedEntity().setCompany(null);
            getEditedEntity().setCleaningMap(null);
            getEditedEntity().setInventory(null);
        }
       // companyField.setValue(this.getEditedEntity().getTaskDocument().getCompany());

    }

}